package afkt.db.feature.room

import afkt.db.R
import afkt.db.database.room.module.note.bean.NoteAndPicture
import afkt.db.database.room.module.note.bean.NotePicture
import afkt.db.database.room.module.note.bean.NoteType
import afkt.db.databinding.AdapterDatabaseBinding
import afkt.db.databinding.AdapterDatabaseImageBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.expand.engine.image.display
import dev.kotlin.utils.toSource
import dev.utils.DevFinal
import dev.utils.app.ViewUtils
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.DateUtils

/**
 * detail: Room 适配器
 * @author Ttt
 */
class RoomAdapter :
    DevDataAdapterExt<NoteAndPicture, DevBaseViewBindingVH<AdapterDatabaseBinding>>() {

    init {
        setPage(0, 8)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterDatabaseBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_database)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterDatabaseBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        val note = item.note
        ViewHelper.get()
            .setText(note.text, holder.binding.vidTitleTv)
            .setText(note.comment, holder.binding.vidContentTv)
            .setText(
                DateUtils.formatDate(note.date, DevFinal.TIME.yyyyMMdd_POINT),
                holder.binding.vidTimeTv
            )
            .setVisibilitys(note.type != NoteType.PICTURE, holder.binding.vidContentTv)
            .setVisibilitys(note.type != NoteType.TEXT, holder.binding.vidRv)
        val imgRecy = holder.binding.vidRv
        if (ViewUtils.isVisibility(imgRecy)) {
            ImageAdapter(item.pictures).bindAdapter(imgRecy)
        }
    }

    internal inner class ImageAdapter(data: List<NotePicture>) :
        DevDataAdapter<NotePicture, DevBaseViewBindingVH<AdapterDatabaseImageBinding>>() {

        init {
            setDataList(data, false)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): DevBaseViewBindingVH<AdapterDatabaseImageBinding> {
            return newBindingViewHolder(parent, R.layout.adapter_database_image)
        }

        override fun onBindViewHolder(
            holder: DevBaseViewBindingVH<AdapterDatabaseImageBinding>,
            position: Int
        ) {
            holder.binding.vidIv.display(
                source = getDataItem(position).picture.toSource()
            )
        }
    }
}