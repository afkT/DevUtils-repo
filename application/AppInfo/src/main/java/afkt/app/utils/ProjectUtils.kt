package afkt.app.utils

import afkt.app.R
import afkt.app.base.AppViewModel
import afkt.app.base.model.DeviceInfo
import afkt.app.base.model.DeviceInfoItem
import afkt.app.base.model.TypeEnum
import android.os.Build
import com.google.gson.GsonBuilder
import dev.kotlin.engine.log.log_e
import dev.kotlin.engine.log.log_eTag
import dev.utils.DevFinal
import dev.utils.app.*
import dev.utils.app.share.SharedUtils
import dev.utils.common.thread.DevThreadManager
import kotlin.math.max

/**
 * detail: 项目工具类
 * @author Ttt
 */
object ProjectUtils {

    // 日志 TAG
    private val TAG = ProjectUtils::class.java.simpleName

    fun toJson(obj: Any?): String? {
        try {
            return GsonBuilder().setPrettyPrinting().create().toJson(obj)
        } catch (e: Exception) {
            TAG.log_eTag(
                throwable = e,
                message = "toJson"
            )
        }
        return null
    }

    /**
     * 重置排序类型索引
     */
    fun resetAppSortType() {
        SharedUtils.put(DevFinal.STR.SORT, 0)
    }

    /**
     * 获取排序类型索引
     */
    fun getAppSortType(): Int {
        val sortPos = SharedUtils.getInt(DevFinal.STR.SORT)
        return max(sortPos, 0)
    }

    /**
     * 获取设备信息
     * @param viewModel [AppViewModel]
     */
    fun getDeviceInfo(viewModel: AppViewModel) {
        DevThreadManager.getInstance(2).execute {
            var lists: ArrayList<DeviceInfoItem> = ArrayList()
            try {
                lists = innerGetDeviceInfo()
            } catch (e: Exception) {
                log_e(throwable = e)
            }
            viewModel.postDeviceInfo(DeviceInfo(TypeEnum.DEVICE_INFO, lists))
        }
    }

    private fun innerGetDeviceInfo(): ArrayList<DeviceInfoItem> {
        val lists = ArrayList<DeviceInfoItem>()
        // 设备信息
        val map = DeviceUtils.getDeviceInfo()
        // 手机型号
        lists.add(DeviceInfoItem(R.string.str_info_model, Build.MODEL))
        // 设备制造商
        lists.add(DeviceInfoItem(R.string.str_info_manufacturer, Build.MANUFACTURER))
        // 设备品牌
        lists.add(DeviceInfoItem(R.string.str_info_brand, Build.BRAND))
        // Android 系统版本
        lists.add(DeviceInfoItem(R.string.str_info_android_version, Build.VERSION.RELEASE))
        // 屏幕尺寸 ( 英寸 )
        lists.add(DeviceInfoItem(R.string.str_info_screen, ScreenUtils.getScreenSizeOfDevice()))
        // 屏幕分辨率
        lists.add(DeviceInfoItem(R.string.str_info_screen_size, ScreenUtils.getScreenSize()))
        // 手机总空间
        lists.add(
            DeviceInfoItem(
                R.string.str_info_sdcard_total,
                SDCardUtils.getAllBlockSizeFormat()
            )
        )
        // 手机可用空间
        lists.add(
            DeviceInfoItem(
                R.string.str_info_sdcard_available,
                SDCardUtils.getAvailableBlocksFormat()
            )
        )
        // 手机总内存
        lists.add(
            DeviceInfoItem(
                R.string.str_info_memory_total,
                MemoryUtils.getTotalMemoryFormat()
            )
        )
        // 手机可用内存
        lists.add(
            DeviceInfoItem(
                R.string.str_info_memory_available,
                MemoryUtils.getMemoryAvailableFormat()
            )
        )
        // 设备版本号
        lists.add(DeviceInfoItem(R.string.str_info_id, Build.ID))
        // 设备版本
        lists.add(DeviceInfoItem(R.string.str_info_display, Build.DISPLAY))
        // 设备名
        lists.add(DeviceInfoItem(R.string.str_info_device, Build.DEVICE))
        // 产品名称
        lists.add(DeviceInfoItem(R.string.str_info_product, Build.PRODUCT))
        // 是否模拟器
        val emulator = map["IS_EMULATOR".lowercase()]
        emulator?.let { lists.add(DeviceInfoItem(R.string.str_info_is_emulator, it)) }
        // 是否允许 debug 调试
        val debuggable = map["IS_DEBUGGABLE".lowercase()]
        debuggable?.let { lists.add(DeviceInfoItem(R.string.str_info_is_debuggable, it)) }
        // 基带版本
        lists.add(DeviceInfoItem(R.string.str_info_baseband_version, DeviceUtils.getBaseband_Ver()))
        // 内核版本
        lists.add(
            DeviceInfoItem(
                R.string.str_info_linuxcode_version,
                DeviceUtils.getLinuxCore_Ver()
            )
        )
        // 序列号
        lists.add(DeviceInfoItem(R.string.str_info_serial, Build.SERIAL))
        // 设备唯一标识, 由设备的多个信息拼接合成
        lists.add(DeviceInfoItem(R.string.str_info_fingerprint, Build.FINGERPRINT))
        // 设备基板名称
        lists.add(DeviceInfoItem(R.string.str_info_board, Build.BOARD))
        // 设备硬件名称, 一般和基板名称一样 BOARD
        lists.add(DeviceInfoItem(R.string.str_info_hardware, Build.HARDWARE))
        // CPU 型号
        lists.add(DeviceInfoItem(R.string.str_info_cpu, CPUUtils.getCpuInfo()))
        // CPU 指令集
        lists.add(DeviceInfoItem(R.string.str_info_cpu_abi1, Build.CPU_ABI))
        lists.add(DeviceInfoItem(R.string.str_info_cpu_abi2, Build.CPU_ABI2))
        // 判断支持的指令集
        val supportedAbis = map["SUPPORTED_ABIS".lowercase()]
        supportedAbis?.let { lists.add(DeviceInfoItem(R.string.str_info_supported_abis, it)) }
        // CPU 数量
        lists.add(
            DeviceInfoItem(
                R.string.str_info_cpu_number,
                CPUUtils.getCoresNumbers().toString()
            )
        )
        // CPU 最高 HZ
        lists.add(DeviceInfoItem(R.string.str_info_cpu_max, CPUUtils.getMaxCpuFreq()))
        // CPU 最底 HZ
        lists.add(DeviceInfoItem(R.string.str_info_cpu_min, CPUUtils.getMinCpuFreq()))
        // CPU 当前 HZ
        lists.add(DeviceInfoItem(R.string.str_info_cpu_cur, CPUUtils.getCurCpuFreq()))
        return lists
    }

    // =

    /**
     * 获取手机屏幕信息
     * @param viewModel [AppViewModel]
     */
    fun getScreenInfo(viewModel: AppViewModel) {
        DevThreadManager.getInstance(2).execute {
            var lists: ArrayList<DeviceInfoItem> = ArrayList()
            try {
                lists = innerGetScreenInfo()
            } catch (e: Exception) {
                log_e(throwable = e)
            }
            viewModel.postScreenInfo(DeviceInfo(TypeEnum.SCREEN_INFO, lists))
        }
    }

    private fun innerGetScreenInfo(): ArrayList<DeviceInfoItem> {
        val lists = ArrayList<DeviceInfoItem>()
        // 屏幕尺寸 ( 英寸 )
        lists.add(DeviceInfoItem(R.string.str_info_screen, ScreenUtils.getScreenSizeOfDevice()))
        // 屏幕分辨率
        lists.add(DeviceInfoItem(R.string.str_info_screen_size, ScreenUtils.getScreenSize()))
        // 屏幕高度
        lists.add(
            DeviceInfoItem(
                R.string.str_info_height_pixels,
                ScreenUtils.getScreenHeight().toString()
            )
        )
        // 屏幕宽度
        lists.add(
            DeviceInfoItem(
                R.string.str_info_width_pixels,
                ScreenUtils.getScreenWidth().toString()
            )
        )
        // X 轴 dpi
        lists.add(DeviceInfoItem(R.string.str_info_xdpi, ScreenUtils.getXDpi().toString()))
        // Y 轴 dpi
        lists.add(DeviceInfoItem(R.string.str_info_ydpi, ScreenUtils.getYDpi().toString()))
        // 屏幕密度
        lists.add(DeviceInfoItem(R.string.str_info_density, ScreenUtils.getDensity().toString()))
        // 屏幕密度 dpi
        lists.add(
            DeviceInfoItem(
                R.string.str_info_density_dpi,
                ScreenUtils.getDensityDpi().toString()
            )
        )
        // 屏幕缩放密度
        lists.add(
            DeviceInfoItem(
                R.string.str_info_scaled_density,
                ScreenUtils.getScaledDensity().toString()
            )
        )
        // 高度 dpi 基准
        lists.add(
            DeviceInfoItem(
                R.string.str_info_height_dpi,
                ScreenUtils.getHeightDpi().toString()
            )
        )
        // 宽度 dpi 基准
        lists.add(DeviceInfoItem(R.string.str_info_width_dpi, ScreenUtils.getWidthDpi().toString()))
        // =
        val builder = StringBuilder()
        builder.append("1dp=").append(SizeUtils.dp2pxf(1F)).append("px")
        builder.append(", 1sp=").append(SizeUtils.sp2pxf(1F)).append("px")
        // 转换 dpi
        lists.add(DeviceInfoItem(R.string.str_info_convert_dpi, builder.toString()))
        return lists
    }
}