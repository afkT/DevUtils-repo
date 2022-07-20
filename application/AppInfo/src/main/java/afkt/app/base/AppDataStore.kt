package afkt.app.base

import afkt.app.base.model.TypeEnum
import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.utils.DevFinal

class AppDataStore {

    // Fragment 类型
    var typeEnum: TypeEnum = TypeEnum.NONE

    fun initDataStore(arguments: Bundle?) {
        arguments?.let {
            // TypeEnum
            val typeInt = it.getInt(DevFinal.STR.TYPE)
            typeEnum = TypeEnum.get(typeInt)
        }
    }

    // 搜索内容
    var searchContent: String = ""
}

/**
 * 设置数据源
 * @param type [TypeEnum]
 */
fun Fragment.setDataStore(type: TypeEnum) {
    val bundle = Bundle()
    bundle.putInt(DevFinal.STR.TYPE, type.tag)
    this.arguments = bundle
}