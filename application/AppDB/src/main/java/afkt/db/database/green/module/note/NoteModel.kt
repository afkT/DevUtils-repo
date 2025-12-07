package afkt.db.database.green.module.note

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
 * GreenDao 的注解处理器目前仅兼容 Java 实体类
 */

///**
// * detail: Note 图片资源
// * @author Ttt
// */
//@Entity
//class NotePicture(
//    @Id(autoincrement = true)
//    var id: Long = 0,
//    // 对应的 note id ( 外键 )
//    var noteId: Long,
//    // 图片链接
//    var picture: String = ""
//)
//
///**
// * detail: Note 实体类
// * @author Ttt
// */
//@Entity(
//    nameInDb = "NoteTable",
//    indexes = [Index(value = "id, date DESC", unique = true)]
//)
//class Note(
//    @Id(autoincrement = true)
//    var id: Long = 0,
//    // 标题
//    @Property(nameInDb = "title123456")
//    var title: String = "",
//    // 内容
//    var content: String = "",
//    // 日期【展示 Converter，也可以直接使用 Long】
//    @Convert(converter = DateConverter::class, columnType = Long::class)
//    var createdAt: Date = Date(),
//    // Note 类型
//    @Convert(converter = NoteTypeConverter::class, columnType = String::class)
//    var type: NoteType = NoteType.ALL
//) {
//
////    @Transient // 数据库不创建该字段
////    private val temp: String? = null
////
////    @ToMany(referencedJoinProperty = "noteId")
////    @OrderBy("id ASC")
////    private val pictures: MutableList<NotePicture>? = null
//}