package afkt.app.ui.adapter

import afkt.app.R
import afkt.app.databinding.AdapterItemKeyValueBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.info.KeyValue

/**
 * detail: 键值对 Adapter
 * @author Ttt
 */
class KeyValueAdapter(data: List<KeyValue>) :
    DevDataAdapterExt<KeyValue, DevBaseViewBindingVH<AdapterItemKeyValueBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterItemKeyValueBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_item_key_value)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterItemKeyValueBinding>,
        position: Int
    ) {
        val item = getDataItem(position)

        ViewHelper.get()
            .setText(item.key, holder.binding.vidKeyTv)
            .setText(item.value, holder.binding.vidValueTv)
            .setOnClick({
                mItemCallback?.onItemClick(item, position)
            }, holder.itemView)
    }
}