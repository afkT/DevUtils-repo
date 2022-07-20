package afkt.app.base.model

import afkt.app.R
import androidx.annotation.StringRes

/**
 * detail: 操作行为类型
 * @author Ttt
 */
enum class ActionEnum {

    // 搜索合并
    COLLAPSE,

    // 搜索展开
    EXPAND,

    // 搜索输入内容
    CONTENT
}

/**
 * detail: Navigation Type
 * @author Ttt
 */
enum class TypeEnum(
    val tag: Int,
    @StringRes val titleId: Int
) {

    NONE(-1, R.string.str_empty),

    // 用户应用信息
    APP_USER(0, R.string.str_user_apps),

    // 系统应用信息
    APP_SYSTEM(1, R.string.str_system_apps),

    // 设备信息
    DEVICE_INFO(2, R.string.str_phone_info),

    // 屏幕信息
    SCREEN_INFO(3, R.string.str_screen_info),

    // 扫描 APK
    QUERY_APK(4, R.string.str_query_apk),

    // 设置
    SETTING(5, R.string.str_setting)

    ;

    companion object {

        /**
         * 获取对应 TAG Type
         * @param tag TAG
         */
        fun get(tag: Int): TypeEnum {
            return when (tag) {
                0 -> APP_USER
                1 -> APP_SYSTEM
                2 -> DEVICE_INFO
                3 -> SCREEN_INFO
                4 -> QUERY_APK
                5 -> SETTING
                else -> NONE
            }
        }
    }
}