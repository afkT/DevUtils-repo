package afkt.app.utils

import com.google.gson.Gson
import dev.utils.DevFinal
import dev.utils.app.share.SharedUtils

/**
 * detail: 搜索后缀 工具类
 * @author Ttt
 */
object QuerySuffixUtils {

    /**
     * 默认配置
     */
    private val dfConfig: String?
        get() {
            val maps = LinkedHashMap<String, String>()
            maps["apk"] = "apk"
            maps["apk.1"] = "apk.1"
            maps["apk.1.1"] = "apk.1.1"
            maps["apk.1.1.1"] = "apk.1.1.1"
            return ProjectUtils.toJson(maps)
        }

    /**
     * 重置处理
     */
    fun reset() {
        SharedUtils.remove(DevFinal.STR.SUFFIX)
    }

    /**
     * 刷新配置
     */
    fun refresh(maps: LinkedHashMap<String, String>?) {
        SharedUtils.put(DevFinal.STR.SUFFIX, Gson().toJson(maps))
    }

    /**
     * 获取搜索后缀
     */
    private val querySuffixStr: String?
        get() = SharedUtils.getString(DevFinal.STR.SUFFIX) ?: dfConfig

    /**
     * 获取搜索后缀
     */
    val querySuffixMap: LinkedHashMap<String, String>
        get() {
            val maps = LinkedHashMap<String, String>()
            maps.putAll(Gson().fromJson(querySuffixStr, maps.javaClass))
            return maps
        }

    /**
     * 获取搜索后缀
     */
    val querySuffixArray: Array<String>
        get() {
            return querySuffixMap.keys.toTypedArray()
        }
}