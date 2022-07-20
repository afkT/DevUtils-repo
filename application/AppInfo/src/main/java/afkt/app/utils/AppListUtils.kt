package afkt.app.utils

import afkt.app.base.model.AppListBean
import afkt.app.base.model.TypeEnum
import dev.callback.DevCallback
import dev.utils.app.info.AppInfoBean
import dev.utils.app.info.AppInfoBean.AppType
import dev.utils.app.info.AppInfoUtils
import dev.utils.common.thread.DevThreadManager
import java.text.Collator
import java.util.*

/**
 * detail: 应用列表工具类
 * @author Ttt
 */
object AppListUtils {

    // 是否获取 APP 列表中
    private var sMapStates = HashMap<AppType, Boolean>()

    // 应用列表数据
    private val sMapAppInfos = HashMap<AppType, ArrayList<AppInfoBean>>()

    // 搜索回调
    private var sCallback: DevCallback<AppListBean>? = null

    /**
     * 设置搜索回调
     * @param callback [DevCallback]
     */
    fun setCallback(callback: DevCallback<AppListBean>) {
        sCallback = callback
    }

    /**
     * 重置
     */
    fun reset() {
        sMapStates.clear()
        sMapAppInfos.clear()
    }

    /**
     * 清空 APP 数据
     * @param appType [AppType]
     */
    fun clearAppData(appType: AppType) {
        sMapAppInfos.remove(appType)
    }

    /**
     * 获取指定 Type App 列表
     * @param type [TypeEnum]
     */
    fun getAppLists(type: TypeEnum) {
        when (type) {
            TypeEnum.APP_USER -> getAppLists(AppType.USER)
            TypeEnum.APP_SYSTEM -> getAppLists(AppType.SYSTEM)
            else -> {}
        }
    }

    /**
     * 获取指定 Type App 列表
     * @param type [TypeEnum]
     * @param refresh 是否刷新
     */
    fun getAppLists(
        type: TypeEnum,
        refresh: Boolean
    ) {
        when (type) {
            TypeEnum.APP_USER -> getAppLists(AppType.USER, refresh)
            TypeEnum.APP_SYSTEM -> getAppLists(AppType.SYSTEM, refresh)
            else -> {}
        }
    }

    // =

    /**
     * 获取指定 Type App 列表
     * @param appType [AppType]
     */
    private fun getAppLists(appType: AppType) {
        getAppLists(appType, false)
    }

    /**
     * 获取指定 Type App 列表
     * @param appType [AppType]
     * @param refresh 是否刷新
     */
    private fun getAppLists(
        appType: AppType,
        refresh: Boolean
    ) {
        val state: Boolean? = sMapStates[appType]
        // 加载中则不处理
        if (state != null && state) {
            return
        }
        val data = sMapAppInfos[appType]
        if (data != null && !refresh) {
            sCallback?.callback(AppListBean(appType, data))
            return
        }
        // 表示加载中
        sMapStates[appType] = true
        // 获取数据
        DevThreadManager.getInstance(3).execute {
            val lists = ArrayList(AppInfoUtils.getAppLists(appType))
            sMapStates[appType] = false
            sMapAppInfos[appType] = lists
            sortAppLists(lists)
            sCallback?.callback(AppListBean(appType, lists))
        }
    }

    // =

    /**
     * 列表排序
     * @param lists 应用列表
     */
    private fun sortAppLists(lists: List<AppInfoBean>) {
        Collections.sort(lists, AppListsComparator(ProjectUtils.getAppSortType()))
    }

    private class AppListsComparator(val sortType: Int) : Comparator<AppInfoBean> {

        val collator: Collator = Collator.getInstance()

        init {
            collator.strength = Collator.PRIMARY
        }

        override fun compare(
            a_app: AppInfoBean,
            b_app: AppInfoBean
        ): Int {
            when (sortType) {
                0 -> { // 按应用名称
                    return a_app.appName.compareTo(b_app.appName)
                }
                1 -> { // 文件大小
                    return if (a_app.apkSize == b_app.apkSize) {
                        0 // 大小相同
                    } else {
                        if (a_app.apkSize > b_app.apkSize) 1 else -1 // 小的前面, 大的后面
                    }
                }
                2 -> { // 安装时间
                    return if (a_app.firstInstallTime == b_app.firstInstallTime) {
                        0 // 安装时间相等
                    } else { // 近期安装的在最前面
                        if (a_app.firstInstallTime > b_app.firstInstallTime) -1 else 1
                    }
                }
                3 -> { // 更新时间
                    return if (a_app.lastUpdateTime == b_app.lastUpdateTime) {
                        0 // 最后更新时间相同
                    } else { // 近期更新的在最前面
                        if (a_app.lastUpdateTime > b_app.lastUpdateTime) -1 else 1
                    }
                }
                else -> return 0
            }
        }
    }
}