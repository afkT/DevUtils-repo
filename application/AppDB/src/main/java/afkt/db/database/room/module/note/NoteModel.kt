package afkt.db.database.room.module.note

import androidx.room.*
import java.util.*

// ===============
// = Note 数据模型 =
// ===============

/**
 * detail: Note 类型
 * @author Ttt
 */
enum class NoteType {
    TEXT,
    PICTURE,
    ALL
}

/**
 * detail: Note 实体类
 * @author Ttt
 * 可以不加 @ColumnInfo 默认为当前字段名, 如果不想创建该列可以增加 @Ignore 注解进行忽略
 */
@Entity(tableName = "NoteTable")
data class Note(
    // 主键 ID
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    // 标题
    @ColumnInfo(name = "title123456")
    var title: String = "",
    // 内容
    var content: String = "",
    // 日期【展示 Converter，也可以直接使用 Long】
    var createdAt: Date = Date(),
    // Note 类型
    var type: NoteType = NoteType.ALL
)

/**
 * detail: Note 图片资源
 * @author Ttt
 */
@Entity(
    tableName = "NotePictureTable",
    // 复合主键：同一笔记下可有多张图片，但每张图片的 (id, noteId) 唯一
    primaryKeys = ["id", "noteId"],
    // 外键：引用 Note.id；删除笔记时级联删除其图片
    foreignKeys = [ForeignKey(
        entity = Note::class,
        parentColumns = ["id"],
        childColumns = ["noteId"],
        // 级联删除
        onDelete = ForeignKey.CASCADE,
        // 级联更新
        onUpdate = ForeignKey.CASCADE
    )],
    // 为外键列建立索引，提升关联查询性能
    indices = [Index(value = ["noteId"])]
)
data class NotePicture(
    var id: Long = 0,
    // 对应的 note id ( 外键 )
    var noteId: Long,
    // 图片链接
    var picture: String = ""
)

/**
 * detail: [Note] 与 [NotePicture] 一对多关联关系类
 * @author Ttt
 */
data class NoteAndPicture(
    @Embedded
    val note: Note,

    @Relation(
        parentColumn = "id",
        entityColumn = "noteId"
    )
    val pictures: MutableList<NotePicture>
)