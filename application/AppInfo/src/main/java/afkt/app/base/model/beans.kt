package afkt.app.base.model

import afkt.app.utils.ProjectUtils
import androidx.annotation.StringRes
import dev.utils.app.ResourceUtils
import dev.utils.app.info.AppInfoBean
import dev.utils.app.info.KeyValue
import java.io.File

/**
 * detail: 设备参数导出实体类
 * @author Ttt
 */
class DeviceInfoBean(
    key: String?,
    value: String?
) : KeyValue(key, value) {

    companion object {

        operator fun get(deviceInfoItem: DeviceInfoItem): DeviceInfoBean {
            val key = ResourceUtils.getString(deviceInfoItem.resId)
            val value = deviceInfoItem.value
            return DeviceInfoBean(key, value)
        }

        fun copyString(deviceInfoItem: DeviceInfoItem): String {
            return get(deviceInfoItem).toString()
        }

        fun jsonString(deviceList: MutableList<DeviceInfoItem>): String? {
            val lists = ArrayList<DeviceInfoBean>()
            for (item in deviceList) lists.add(get(item))
            return ProjectUtils.toJson(lists)
        }
    }
}

/**
 * detail: 设备、屏幕信息
 * @author Ttt
 */
class DeviceInfo(
    val type: TypeEnum,
    val lists: ArrayList<DeviceInfoItem>
)

/**
 * detail: 搜索事件
 * @author Ttt
 */
class SearchEvent(
    val type: TypeEnum,
    val action: ActionEnum,
    var content: String = ""
)

/**
 * detail: 应用列表搜索实体类
 * @author Ttt
 */
class AppListBean(
    appType: AppInfoBean.AppType,
    val lists: ArrayList<AppInfoBean>
) {

    val type: TypeEnum = when (appType) {
        AppInfoBean.AppType.USER -> {
            TypeEnum.APP_USER
        }
        AppInfoBean.AppType.SYSTEM -> {
            TypeEnum.APP_SYSTEM
        }
        AppInfoBean.AppType.ALL -> {
            TypeEnum.NONE
        }
    }
}

/**
 * detail: 文件资源 Item
 * @author Ttt
 */
class FileApkItem(
    val file: File,
    // 文件名字 ( 前缀.后缀 )
    val name: String,
    // 文件地址
    val uri: String,
    // APP 信息
    val appInfoBean: AppInfoBean,
    // 文件最后操作时间
    val lastModified: Long = file.lastModified()
)

/**
 * detail: 设备信息 Item
 * @author Ttt
 */
class DeviceInfoItem(
    // 提示文案
    @StringRes val resId: Int,
    // 配置值
    val value: String
)