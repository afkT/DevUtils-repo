package afkt.app.ui.adapter

import afkt.app.R
import afkt.app.base.model.FileApkItem
import afkt.app.databinding.AdapterItemAppBinding
import afkt.app.ui.activity.ApkDetailsActivity
import android.content.Intent
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.utils.DevFinal
import dev.utils.app.AppUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils

/**
 * detail: App 列表 Adapter
 * @author Ttt
 */
class ApkListAdapter :
    DevDataAdapterExt<FileApkItem, DevBaseViewBindingVH<AdapterItemAppBinding>>() {

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
        val appInfo = item.appInfoBean

        ViewHelper.get()
            .setText(appInfo.appName, holder.binding.vidNameTv)
            .setText(appInfo.appPackName, holder.binding.vidPackTv)
            .setImageDrawable(appInfo.appIcon, holder.binding.vidIv)
            .setOnClick({
                if (FileUtils.isFileExists(item.uri)) {
                    val intent = Intent(context, ApkDetailsActivity::class.java)
                    intent.putExtra(DevFinal.STR.URI, item.uri)
                    AppUtils.startActivity(intent)
                } else {
                    ToastTintUtils.warning(ResourceUtils.getString(R.string.str_file_not_exist))
                }
            }, holder.itemView)

        if (FileUtils.isFileExists(item.uri)) {
            ViewHelper.get()
                .setAntiAliasFlag(holder.binding.vidNameTv)
                .setAntiAliasFlag(holder.binding.vidPackTv)
        } else {
            ViewHelper.get()
                .setStrikeThruText(holder.binding.vidNameTv)
                .setStrikeThruText(holder.binding.vidPackTv)
        }
    }
}