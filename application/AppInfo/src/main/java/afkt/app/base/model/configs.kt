package afkt.app.base.model

import dev.utils.app.MediaStoreUtils
import dev.utils.app.PathUtils
import java.io.File

/**
 * detail: App 配置
 * @author Ttt
 */
object AppConfig {

    // ==========
    // = 项目信息 =
    // ==========

    // 项目名
    const val BASE_NAME = "AppInfo"

    // ==========
    // = 其他配置 =
    // ==========

    // 项目日志 TAG
    const val LOG_TAG = BASE_NAME + "_Log"
}

/**
 * detail: 文件路径配置
 * @author Ttt
 */
object PathConfig {

    // =============
    // = 应用外部存储 =
    // =============

    // ==========
    // = 外部存储 =
    // ==========

    // 应用外部存储
    private val BASE_APP_EXTERNAL_PATH = MediaStoreUtils.RELATIVE_DOWNLOAD_PATH

    // 统一文件夹
    val AEP_PATH = BASE_APP_EXTERNAL_PATH + File.separator + AppConfig.BASE_NAME + File.separator

    // APP 信息导出地址
    val AEP_APPMSG_PATH = AEP_PATH + "AppMsg" + File.separator

    // APK 信息导出地址
    val AEP_APKMSG_PATH = AEP_PATH + "ApkMsg" + File.separator

    // ==========
    // = 内部存储 =
    // ==========

    // 应用内部存储
    private val BASE_APP_INTERNAL_PATH = PathUtils.getAppExternal().appDataPath

    // 统一文件夹
    val AIP_PATH = BASE_APP_INTERNAL_PATH + File.separator + AppConfig.BASE_NAME + File.separator

    // APK 文件导出地址 ( 可通过分享 )
    val AIP_APK_PATH = AIP_PATH + "APK" + File.separator
}