package afkt.app.utils

import afkt.app.R
import afkt.app.base.model.PathConfig
import android.content.Intent
import dev.base.DevSource
import dev.engine.DevEngine
import dev.engine.storage.OnDevInsertListener
import dev.engine.storage.StorageItem
import dev.engine.storage.StorageResult
import dev.utils.app.AppUtils
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.UriUtils
import dev.utils.app.info.ApkInfoItem
import dev.utils.app.info.AppInfoBean
import dev.utils.app.info.AppInfoItem
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils
import dev.utils.common.thread.DevThreadManager
import java.io.File

/**
 * detail: 导出工具类 ( 合并减少代码 )
 * @author Ttt
 */
object ExportUtils {

    interface Callback {
        fun onExport(file: File)
    }

    // ==========
    // = 导出信息 =
    // ==========

    /**
     * 导出 APP 信息
     */
    fun exportInfo(appInfoItem: AppInfoItem) {
        exportAppInfo(true, appInfoItem.appInfoBean, ProjectUtils.toJson(appInfoItem))
    }

    /**
     * 导出 APK 信息
     */
    fun exportInfo(apkInfoItem: ApkInfoItem) {
        exportAppInfo(false, apkInfoItem.appInfoBean, ProjectUtils.toJson(apkInfoItem))
    }

    /**
     * 导出信息
     * @param isApp 是否 app
     * @param appInfoBean App 信息
     * @param info 导出信息
     */
    private fun exportAppInfo(
        isApp: Boolean,
        appInfoBean: AppInfoBean,
        info: String?
    ) {
        val folder = if (isApp) PathConfig.AEP_APPMSG_PATH else PathConfig.AEP_APKMSG_PATH
        // 应用名_包名_版本名.txt
        val fileName =
            appInfoBean.appName + "_" + appInfoBean.appPackName + "_" + appInfoBean.versionName + ".txt"
        exportInfo(fileName, folder, info)
    }

    /**
     * 最终导出信息方法
     * @param fileName 文件名
     * @param folder 存储文件夹
     * @param info 导出信息
     */
    fun exportInfo(
        fileName: String,
        folder: String,
        info: String?
    ) {
        DevEngine.getStorage()?.apply {
            insertDownloadToExternal(
                StorageItem.createExternalItemFolder(
                    fileName, folder
                ),
                DevSource.create(
                    info?.toByteArray()
                ),
                object : OnDevInsertListener {
                    override fun onResult(
                        result: StorageResult,
                        params: StorageItem?,
                        source: DevSource?
                    ) {
                        if (result.isSuccess()) {
                            ToastTintUtils.success(
                                ResourceUtils.getString(R.string.str_export_suc)
                                        + "\n" + FileUtils.getAbsolutePath(result.getFile())
                            )
                        } else {
                            ToastTintUtils.error(ResourceUtils.getString(R.string.str_export_fail))
                        }
                    }
                }
            )
        }
    }

    // ==========
    // = 导出应用 =
    // ==========

    /**
     * 导出 APP
     */
    fun exportApp(appInfoItem: AppInfoItem) {
        exportApp(appInfoItem.appInfoBean, null)
    }

    /**
     * 导出 APK
     */
    fun exportApp(apkInfoItem: ApkInfoItem) {
        exportApp(apkInfoItem.appInfoBean, null)
    }

    /**
     * 导出信息
     * @param appInfoBean App 信息
     * @param callback 导出回调
     */
    private fun exportApp(
        appInfoBean: AppInfoBean,
        callback: Callback?
    ) {
        DevThreadManager.getInstance(3).execute {
            // 应用名_包名_版本名.apk
            val fileName =
                appInfoBean.appName + "_" + appInfoBean.appPackName + "_" + appInfoBean.versionName + ".apk"

            val destFile = FileUtils.getFile(PathConfig.AIP_APK_PATH, fileName)
            val destPath = destFile.absolutePath
            if (destFile.exists()) {
                ToastTintUtils.success(
                    ResourceUtils.getString(R.string.str_export_suc) + " " + destPath
                )
                callback?.let { HandlerUtils.postRunnable { it.onExport(destFile) } }
                return@execute
            }
            // 提示导出中
            ToastTintUtils.normal(ResourceUtils.getString(R.string.str_export_ing))
            // 导出应用
            val result = FileUtils.copyFile(
                appInfoBean.sourceDir, destPath, true
            )
            if (result) {
                ToastTintUtils.success(
                    ResourceUtils.getString(R.string.str_export_suc) + " " + destPath
                )
                callback?.let { HandlerUtils.postRunnable { it.onExport(destFile) } }
            } else {
                ToastTintUtils.error(ResourceUtils.getString(R.string.str_export_fail))
            }
        }
    }

    // ==========
    // = 分享应用 =
    // ==========

    /**
     * 分享 APP
     */
    fun shareApp(appInfoItem: AppInfoItem) {
        exportApp(appInfoItem.appInfoBean, object : Callback {
            override fun onExport(file: File) {
                shareApp(file)
            }
        })
    }

    /**
     * 分享 APK
     */
    fun shareApp(apkInfoItem: ApkInfoItem) {
        exportApp(apkInfoItem.appInfoBean, object : Callback {
            override fun onExport(file: File) {
                shareApp(file)
            }
        })
    }

    /**
     * 分享
     */
    private fun shareApp(file: File) {
        try {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_STREAM, UriUtils.getUriForFile(file))
            if (AppUtils.startActivity(intent)) return
        } catch (e: java.lang.Exception) {
        }
        ToastTintUtils.error(ResourceUtils.getString(R.string.str_share_fail))
    }
}