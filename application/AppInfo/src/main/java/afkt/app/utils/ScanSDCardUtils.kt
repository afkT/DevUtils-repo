package afkt.app.utils

import afkt.app.base.model.FileApkItem
import dev.callback.DevCallback
import dev.kotlin.engine.log.log_d
import dev.utils.app.PathUtils
import dev.utils.app.info.AppInfoUtils
import dev.utils.common.StringUtils
import dev.utils.common.assist.search.FileBreadthFirstSearchUtils
import dev.utils.common.thread.DevThreadPool
import java.io.File
import java.util.*

/**
 * detail: 扫描 SDCard 工具类
 * @author Ttt
 */
class ScanSDCardUtils private constructor() {

    companion object {

        val instance: ScanSDCardUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ScanSDCardUtils()
        }
    }

    // 文件广度优先搜索算法 ( 多线程 + 队列, 搜索某个目录下的全部文件 )
    private var utils: FileBreadthFirstSearchUtils

    // 搜索后缀
    private var querySuffixArray: Array<String> = arrayOf("")

    // 搜索后的数据 ( 节省资源, 不遍历 FileBreadthFirstSearchUtils.FileItem )
    private var files: ArrayList<File> = ArrayList()

    // 转换后的数据
    private var data: ArrayList<FileApkItem>? = null

    // 搜索回调
    private var sCallback: DevCallback<ArrayList<FileApkItem>>? = null

    /**
     * 设置搜索回调
     * @param callback [DevCallback]
     */
    fun setCallback(callback: DevCallback<ArrayList<FileApkItem>>) {
        sCallback = callback
    }

    init {
        utils = FileBreadthFirstSearchUtils(object : FileBreadthFirstSearchUtils.SearchHandler {
            override fun isHandlerFile(file: File?): Boolean {
                return true
            }

            override fun isAddToList(file: File): Boolean {
                if (file.exists()) {
                    if (StringUtils.isEndsWith(true, file.name, *querySuffixArray)) {
                        files.add(file)
                        return true
                    }
                }
                return false
            }

            override fun onEndListener(
                rootFileItem: FileBreadthFirstSearchUtils.FileItem?,
                startTime: Long,
                endTime: Long
            ) {
                log_d(message = "搜索耗时: ${endTime - startTime} ms")
                val lists = convertList()
                Collections.sort(lists, ApkListsComparator())
                data = lists
                sCallback?.callback(lists)
            }
        })
        utils.setQueueSameTimeNumber(
            DevThreadPool.getCalcThreads()
        ).delayTime = 100L
    }

    /**
     * 开始搜索
     */
    fun query() {
        query(false)
    }

    /**
     * 开始搜索
     * @param refresh 是否刷新
     */
    fun query(refresh: Boolean) {
        if (data != null && !refresh) {
            sCallback?.callback(data)
            return
        }
        if (utils.isRunning) return
        querySuffixArray = QuerySuffixUtils.querySuffixArray
        files = ArrayList()
        utils.query(PathUtils.getSDCard().sdCardPath)
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 转换处理
     */
    private fun convertList(): ArrayList<FileApkItem> {
        val lists = ArrayList<FileApkItem>()
        for (file in files) {
            val appInfoBean = AppInfoUtils.getAppInfoBeanToPath(file.path)
            appInfoBean?.let {
                lists.add(
                    FileApkItem(
                        file, file.name, file.path,
                        appInfoBean
                    )
                )
            }
        }
        return lists
    }

    private class ApkListsComparator : Comparator<FileApkItem> {
        override fun compare(
            a_apk: FileApkItem,
            b_apk: FileApkItem
        ): Int {
            return if (b_apk != null) {
                if (a_apk.lastModified == b_apk.lastModified) {
                    0 // 安装时间相等
                } else { // 近期安装的在最前面
                    if (a_apk.lastModified > b_apk.lastModified) -1 else 1
                }
            } else 0
        }
    }
}