package afkt.app.ui.adapter

import afkt.app.R
import afkt.app.databinding.AdapterItemAppBinding
import afkt.app.ui.activity.AppDetailsActivity
import android.content.Intent
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.utils.DevFinal
import dev.utils.app.AppUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.info.AppInfoBean
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: App 列表 Adapter
 * @author Ttt
 */
class AppListAdapter(data: List<AppInfoBean>?) :
    DevDataAdapterExt<AppInfoBean, DevBaseViewBindingVH<AdapterItemAppBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterItemAppBinding> {
        parentContext(parent)
        return newBindingViewHolder(parent, R.layout.adapter_item_app)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterItemAppBinding>,
        position: Int
    ) {
        val item = getDataItem(position)

        ViewHelper.get()
            .setText(item.appName, holder.binding.vidNameTv)
            .setText(item.appPackName, holder.binding.vidPackTv)
            .setImageDrawable(item.appIcon, holder.binding.vidIv)
            .setOnClick({
                if (AppUtils.isInstalledApp(item.appPackName)) {
                    val intent = Intent(context, AppDetailsActivity::class.java)
                    intent.putExtra(DevFinal.STR.PACKNAME, item.appPackName)
                    AppUtils.startActivity(intent)
                } else {
                    ToastTintUtils.warning(ResourceUtils.getString(R.string.str_app_not_exist))
                }
            }, holder.itemView)
    }
}