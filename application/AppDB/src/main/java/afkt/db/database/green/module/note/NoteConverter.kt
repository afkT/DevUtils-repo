package afkt.db.database.green.module.note

import org.greenrobot.greendao.converter.PropertyConverter
import java.util.*

/**
 * detail: NoteType 类型转换
 * @author Ttt
 */
class NoteTypeConverter : PropertyConverter<NoteType, String> {
    override fun convertToEntityProperty(databaseValue: String?): NoteType? {
        return databaseValue?.let { runCatching { NoteType.valueOf(it) }.getOrNull() }
    }

    override fun convertToDatabaseValue(entityProperty: NoteType?): String? {
        return entityProperty?.name
    }
}

/**
 * detail: Date 类型转换
 * @author Ttt
 */
class DateConverter : PropertyConverter<Date, Long> {
    override fun convertToEntityProperty(databaseValue: Long?): Date? {
        return if (databaseValue == 0L || databaseValue == null) null else Date(databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: Date?): Long {
        return entityProperty?.time ?: 0L
    }
}