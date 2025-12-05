package afkt.db.feature

import afkt.db.BR
import afkt.db.R
import dev.simple.core.adapter.AdapterModel
import me.tatarka.bindingcollectionadapter2.ItemBinding

// =======================
// = Note Adapter 数据模型 =
// =======================

/**
 * detail: Note Adapter 数据模型
 * @author Ttt
 */
class NoteItem(
    // Note Id
    val id: Long,
    // 标题
    val title: String,
    // 内容
    val content: String,
    // 创建时间
    val createdAt: String,
    // 是否存在图片
    val isPictureType: Boolean
) {
    val pictureAdapter = NotePictureAdapterModel()
}

/**
 * detail: Note Adapter 模型
 * @author Ttt
 */
class NoteAdapterModel : AdapterModel<NoteItem>() {

    // Item Binding
    val itemBinding = ItemBinding.of<NoteItem>(
        BR.itemValue, R.layout.adapter_item_note
    )
}

/**
 * detail: Note Picture Adapter 模型
 * @author Ttt
 */
class NotePictureAdapterModel : AdapterModel<String>() {

    // Item Binding
    val itemBinding = ItemBinding.of<String>(
        BR.itemValue, R.layout.adapter_item_note_picture
    )
}