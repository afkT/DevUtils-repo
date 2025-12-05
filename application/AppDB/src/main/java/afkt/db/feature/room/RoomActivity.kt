package afkt.db.feature.room

import afkt.db.AppRouter
import afkt.db.BR
import afkt.db.R
import afkt.db.base.BaseActivity
import afkt.db.base.BaseViewModel
import afkt.db.database.room.module.note.NoteAndPicture
import afkt.db.databinding.ActivityRoomBinding
import afkt.db.feature.NoteAdapterModel
import afkt.db.feature.NoteItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.therouter.router.Route
import dev.base.simple.extensions.asActivity
import dev.utils.common.CollectionUtils
import dev.utils.common.DateUtils
import dev.utils.common.StringUtils

/**
 * detail: Room 使用
 * @author Ttt
 */
@Route(path = AppRouter.PATH_ROOM_ACTIVITY)
class RoomActivity : BaseActivity<ActivityRoomBinding, RoomViewModel>(
    R.layout.activity_room, BR.viewModel, simple_Agile = { act ->
        act.asActivity<RoomActivity> {
            binding.vidTitle.leftView.setOnClickListener { finish() }
            // 初始化操作
            viewModel.initialize(this)
        }
    }
)

class RoomViewModel : BaseViewModel() {

    val adapter = NoteAdapterModel()

    val clickInsert = View.OnClickListener {
        RoomMockData.insertNodes()
    }

    // ============
    // = 初始化方法 =
    // ============

    fun initialize(activity: AppCompatActivity) {
    }
}

// =====================
// = Adapter 数据模型转换 =
// =====================

// =================
// = Room Database =
// =================

private fun NoteAndPicture.toNoteItem(): NoteItem {
    val pictureList = pictures.map { it.picture }
    return NoteItem(
        id = note.id,
        title = note.title,
        content = note.content,
        createdAt = StringUtils.checkValue(
            DateUtils.formatDate(note.createdAt)
        ),
        isPictureType = CollectionUtils.isNotEmpty(
            pictureList
        )
    ).apply {
        if (isPictureType) {
            pictureAdapter.addAllAndClear(pictureList)
        }
    }
}

/**
 * detail: Note Adapter 数据模型转换
 * @author Ttt
 */
fun NoteAdapterModel.convertItems(source: List<NoteAndPicture>) {
    val result = source.map { it.toNoteItem() }
    addAllAndClear(result)
}