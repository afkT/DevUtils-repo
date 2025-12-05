package afkt.db.feature

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import dev.simple.extensions.size.AppSize
import dev.utils.app.RecyclerViewUtils
import dev.widget.decoration.linear.LinearColorItemDecoration

// ===============================
// = RecyclerView BindingAdapter =
// ===============================

@BindingAdapter(
    value = [
        "binding_item_touch_helper",
//        "binding_item_touch_move",
//        "binding_item_touch_swiped"
    ]
)
fun RecyclerView.bindingItemTouchHelper(
    init: Boolean,
//    moveBlock: () -> Unit,
//    swipedBlock: () -> Unit,
) {
    val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        /**
         * 获取动作标识
         * 动作标识分 : dragFlags 和 swipeFlags
         * dragFlags : 列表滚动方向的动作标识 ( 如竖直列表就是上和下, 水平列表就是左和右 )
         * wipeFlags : 与列表滚动方向垂直的动作标识 ( 如竖直列表就是左和右, 水平列表就是上和下 )
         */
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            // 如果你不想上下拖动, 可以将 dragFlags = 0
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN

            // 如果你不想左右滑动, 可以将 swipeFlags = 0
            val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

            // 最终的动作标识 ( flags ) 必须要用 makeMovementFlags() 方法生成
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        /**
         * 是否开启 item 长按拖拽功能
         */
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.bindingAdapterPosition
            val toPosition = target.bindingAdapterPosition
//            Collections.swap(adapter.dataList, fromPosition, toPosition)
            adapter?.notifyItemMoved(fromPosition, toPosition)
            return true
        }

        /**
         * 当 item 侧滑出去时触发 ( 竖直列表是侧滑, 水平列表是竖滑 )
         * @param viewHolder
         * @param direction 滑动的方向
         */
        override fun onSwiped(
            viewHolder: RecyclerView.ViewHolder,
            direction: Int
        ) {
            val position = viewHolder.bindingAdapterPosition
            if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
//                val nap = adapter.dataList.removeAt(position)
//                adapter?.notifyItemRemoved(position)
//                // 删除文章
//                RoomManager.getNoteDatabase().noteDao.deleteNote(nap.note)
//                // 删除图片
//                if (CollectionUtils.isNotEmpty(nap.pictures)) {
//                    val deleteCount = RoomManager.getNoteDatabase().noteDao
//                        .deleteNotePictures(
//                            *CollectionUtils.toArrayT(nap.pictures)
//                        )
//                    TAG.log_dTag(
//                        message = "删除图片数量: $deleteCount"
//                    )
//                }
            }
        }
    })
    itemTouchHelper.attachToRecyclerView(this)
}

/**
 * 设置 RecyclerView 边距 5dp
 */
@BindingAdapter("binding_item_decoration_5dp")
fun RecyclerView.bindingItemDecoration_5dp(
    addItemDecoration: Boolean
) {
    bindingItemDecorationDP(5F, addItemDecoration)
}

// ==========
// = 内部方法 =
// ==========

/**
 * 设置 RecyclerView 边距 DP
 */
private fun RecyclerView.bindingItemDecorationDP(
    dpValue: Float,
    addItemDecoration: Boolean
) {
    if (addItemDecoration) {
        if (RecyclerViewUtils.getItemDecorationCount(this) == 0) {
            val manager = layoutManager
            if (manager is LinearLayoutManager) {
                val item = LinearColorItemDecoration(
                    manager.canScrollVertically(),
                    AppSize.dp2pxf(context, dpValue)
                )
                addItemDecoration(item)
            }
        }
    } else {
        RecyclerViewUtils.removeAllItemDecoration(this)
    }
}

@BindingAdapter("binding_item_animator")
fun RecyclerView.bindingItemAnimator(
    animator: ItemAnimator? = null
) {
    itemAnimator = animator
}