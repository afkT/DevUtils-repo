package afkt.app.ui.adapter

import afkt.app.R
import afkt.app.base.model.DeviceInfoBean
import afkt.app.base.model.DeviceInfoItem
import afkt.app.databinding.AdapterItemDeviceInfoBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.utils.app.ClipboardUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 设备、屏幕信息 Adapter
 * @author Ttt
 */
class InfoAdapter :
    DevDataAdapterExt<DeviceInfoItem, DevBaseViewBindingVH<AdapterItemDeviceInfoBinding>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterItemDeviceInfoBinding> {
        parentContext(parent)
        return newBindingViewHolder(parent, R.layout.adapter_item_device_info)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterItemDeviceInfoBinding>,
        position: Int
    ) {
        val item = getDataItem(position)

        ViewHelper.get()
            .setText(item.value, holder.binding.vidValueTv)
            .setText(ResourceUtils.getString(item.resId), holder.binding.vidKeyTv)
            .setOnClick({
                val txt: String = DeviceInfoBean.copyString(item)
                // 复制到剪切板
                ClipboardUtils.copyText(txt)
                // 进行提示
                ToastTintUtils.success(ResourceUtils.getString(R.string.str_copy_suc) + ", " + txt)
            }, holder.itemView)
    }
}