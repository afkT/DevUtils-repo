package afkt.db.database.room.module.note

import androidx.room.TypeConverter
import java.util.*

/**
 * detail: Note 数据库实体类转换
 * @author Ttt
 */
class NoteConverter {

    @TypeConverter
    fun convertToEntityPropertyDate(databaseValue: Long): Date? {
        return if (databaseValue == 0L) null else Date(databaseValue)
    }

    @TypeConverter
    fun convertToDatabaseValueDate(entityProperty: Date?): Long {
        return entityProperty?.time ?: 0L
    }

    @TypeConverter
    fun convertToEntityPropertyNoteType(databaseValue: String?): NoteType? {
        return databaseValue?.let { runCatching { NoteType.valueOf(it) }.getOrNull() }
    }

    @TypeConverter
    fun convertToDatabaseValueNoteType(entityProperty: NoteType?): String? {
        return entityProperty?.name
    }

//    @TypeConverter
//    fun convertToEntityPropertyNotePicture(databaseValue: String?): List<NotePicture>? {
//        return databaseValue?.let {
//            runCatching {
//                it.fromJson<List<NotePicture>>(
//                    typeOfT = TypeUtils.getListType(NotePicture::class.java)
//                )
//            }.getOrNull()
//        }
//    }
//
//    @TypeConverter
//    fun convertToDatabaseValueNotePicture(entityProperty: List<NotePicture>?): String? {
//        return entityProperty?.toJson()
//    }
}