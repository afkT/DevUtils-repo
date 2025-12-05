package afkt.db.feature.green_dao

import afkt.db.AppRouter
import afkt.db.BR
import afkt.db.R
import afkt.db.base.BaseActivity
import afkt.db.base.BaseViewModel
import afkt.db.database.room.module.note.NoteAndPicture
import afkt.db.database.room.module.note.NoteDatabase
import afkt.db.databinding.ActivityGreenDaoBinding
import afkt.db.feature.NoteAdapterModel
import afkt.db.feature.NoteItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.therouter.router.Route
import dev.base.simple.extensions.asActivity
import dev.simple.extensions.hi.hiif.hiIfNotNull
import dev.utils.common.CollectionUtils
import dev.utils.common.DateUtils
import dev.utils.common.StringUtils

/**
 * detail: GreenDao 使用
 * @author Ttt
 */
@Route(path = AppRouter.PATH_GREEN_DAO_ACTIVITY)
class GreenDaoActivity : BaseActivity<ActivityGreenDaoBinding, GreenDaoViewModel>(
    R.layout.activity_green_dao, BR.viewModel, simple_Agile = { act ->
        act.asActivity<GreenDaoActivity> {
            binding.vidTitle.leftView.setOnClickListener { finish() }
            // 初始化操作
            viewModel.initialize(this)
        }
    }
)

class GreenDaoViewModel : BaseViewModel() {

    // 最后一条数据 ID
    var lastId = 0L

    // Note Adapter 模型
    val adapter = NoteAdapterModel()

    // 点击添加数据事件
    val clickInsert = View.OnClickListener {
        GreenDaoMockData.insertNodes()
    }

    // ============
    // = 初始化方法 =
    // ============

    fun initialize(activity: AppCompatActivity) {
        NoteDatabase.database()?.noteDao.hiIfNotNull { dao ->
            dao.getNoteAndPictureListsAfterId(
                lastId, 50
            ).observe(activity) {
                adapter.convertItems(it)
            }
        }
    }
}

// =====================
// = Adapter 数据模型转换 =
// =====================

// =====================
// = GreenDao Database =
// =====================

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