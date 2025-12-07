package afkt.db.feature.green_dao

import afkt.db.AppRouter
import afkt.db.BR
import afkt.db.R
import afkt.db.base.BaseActivity
import afkt.db.base.BaseViewModel
import afkt.db.database.green.module.note.NoteDatabase
import afkt.db.database.green.module.note.bean.Note
import afkt.db.databinding.ActivityGreenDaoBinding
import afkt.db.feature.NoteAdapterModel
import afkt.db.feature.NoteItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.therouter.router.Route
import dev.base.simple.extensions.asActivity
import dev.simple.extensions.hi.hiif.hiIfNotNull
import dev.utils.common.CollectionUtils
import dev.utils.common.DateUtils
import dev.utils.common.StringUtils
import gen.greendao.NoteDao
import gen.greendao.NotePictureDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import java.util.*

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
            viewModel.initialize(this, binding.vidRefresh)
        }
    }
)

class GreenDaoViewModel : BaseViewModel() {

    private var refreshLayout = WeakReference<SmartRefreshLayout>(null)

    // 是否加载中
    private var isLoading = false

    // 请求条数
    private val PAGE_SIZE = 10

    // 最后一条数据 ID
    private var _lastId = 0L

    // Note Adapter 模型
    val adapterModel = NoteAdapterModel()

    // 点击添加数据事件
    val clickInsert = View.OnClickListener {
        GreenDaoMockData.insertNotes()
        // 添加新的数据则更新加载更多状态
        refreshLayout.get().hiIfNotNull { layout ->
            // 是否有更多数据 `true` 无数据, `false` 还有数据
            layout.setNoMoreData(false)
        }
    }

    // ============
    // = 初始化方法 =
    // ============

    fun initialize(
        activity: AppCompatActivity,
        layout: SmartRefreshLayout
    ) {
        // 初始化刷新 View
        initializeRefreshView(layout)
    }

    /**
     * 初始化刷新 View
     */
    private fun initializeRefreshView(layout: SmartRefreshLayout) {
        refreshLayout = WeakReference(layout)
        // 设置刷新事件监听
        layout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                refreshList()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                requestLoadMore()
            }
        })
        // 自动刷新
        layout.autoRefresh()
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 请求操作 ID
    private var opUUID = UUID.randomUUID()

    /**
     * 更新请求 UUID
     */
    private fun updateUUID(): UUID {
        opUUID = UUID.randomUUID()
        return opUUID
    }

    /**
     * 完成刷新 or 加载
     */
    private fun finishRefreshAndLoad() {
        isLoading = false
        refreshLayout.get()?.apply {
            if (isRefreshing) {
                finishRefresh()
            } else if (isLoading) {
                finishLoadMore()
            }
        }
    }

    /**
     * 刷新列表
     */
    private fun refreshList() {
        if (isLoading) return
        isLoading = true
        requestList(updateUUID(), 0L)
    }

    /**
     * 请求加载更多【自动请求下一页】
     */
    private fun requestLoadMore() {
        if (isLoading) return
        isLoading = true
        requestList(updateUUID(), _lastId)
    }

    /**
     * 请求列表数据
     * @param uuid 请求 UUID
     * @param lastId 最后一条数据 ID
     */
    private fun requestList(
        uuid: UUID,
        lastId: Long
    ) {
        NoteDatabase.database()?.noteDao().hiIfNotNull { dao ->
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    // 构建查询条件
                    dao.queryBuilder()
                        // id > lastId
                        .where(NoteDao.Properties.Id.gt(lastId))
                        // 按 id 升序排序
                        .orderAsc(NoteDao.Properties.Id)
                        // 每页数量
                        .limit(PAGE_SIZE)
                        // 获取数据
                        .list()
                }
                if (uuid == opUUID) {
                    val size = adapterModel.convertItems(lastId, result)
                    // 更新最后一条数据 ID
                    _lastId = adapterModel.last()?.id ?: lastId
                    // 完成刷新 or 加载
                    finishRefreshAndLoad()
                    // 判断是否存在数据
                    refreshLayout.get().hiIfNotNull { layout ->
                        // 是否有更多数据 `true` 无数据, `false` 还有数据
                        val noMoreData = size != PAGE_SIZE
                        layout.setNoMoreData(noMoreData)
                    }
                    if (adapterModel.isNotEmpty()) {
                        // 更新请求 UUID
                        updateUUID()
                    }
                }
            }
        }
    }

    // ===================
    // = ItemTouchHelper =
    // ===================

    // 是否开启 item 长按拖拽功能
    val touchMoveBlock: (
        recyclerView: RecyclerView,
        fromPosition: Int,
        toPosition: Int
    ) -> Unit = { _, fromPosition, toPosition ->
        Collections.swap(adapterModel.items, fromPosition, toPosition)
    }

    val touchSwipedBlock: (
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) -> Unit = { viewHolder, direction ->
        if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
            val position = viewHolder.bindingAdapterPosition
            adapterModel.items.removeAt(position).hiIfNotNull { note ->
                // 删除数据库数据
                NoteDatabase.database().hiIfNotNull { database ->
                    database.noteDao().hiIfNotNull { dao ->
                        // 删除 Note
                        dao.deleteByKey(note.id)
                    }
                    database.notePictureDao().hiIfNotNull { dao ->
                        // 删除 NotePicture
                        val deleteQuery = dao.queryBuilder()
                            .where(NotePictureDao.Properties.NoteId.eq(note.id))
                            .buildDelete()
                        deleteQuery.executeDeleteWithoutDetachingEntities()
                    }
                }
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

private fun Note.toNoteItem(): NoteItem {
    val pictureList = pictures.map { it.picture }
    return NoteItem(
        id = id,
        title = "${id}. ${title}",
        content = content,
        createdAt = StringUtils.checkValue(
            DateUtils.formatDate(createdAt)
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
fun NoteAdapterModel.convertItems(
    lastId: Long,
    source: List<Note>
): Int {
    val result = source.map { it.toNoteItem() }
    if (lastId == 0L) {
        addAllAndClear(result)
    } else {
        addAll(result)
    }
    return result.size
}