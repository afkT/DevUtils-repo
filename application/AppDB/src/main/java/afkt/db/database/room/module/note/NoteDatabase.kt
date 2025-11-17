package afkt.db.database.room.module.note

import afkt.db.database.room.AbstractRoomDatabase
import afkt.db.database.room.RoomManager
import android.text.TextUtils
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import dev.DevUtils

/**
 * detail: Room 数据库创建
 * @author Ttt
 * exportSchema = true 导出 JSON 文件
 * build.gradle
 * arguments += ["room.schemaLocation": "$projectDir/schemas".toString()]
 */
@Database(entities = [Note::class, NotePicture::class], version = 1, exportSchema = false)
@TypeConverters(value = [NoteConverter::class])
abstract class NoteDatabase : AbstractRoomDatabase() {

    // =======
    // = Dao =
    // =======

    /**
     * 获取 [Note] Dao
     * @return [NoteDao]
     */
    abstract val noteDao: NoteDao?

    // ============
    // = database =
    // ============

    companion object {

        // 日志 TAG
        val TAG: String = NoteDatabase::class.java.getSimpleName()

        /**
         * 创建数据库
         * @param dbName 数据库名
         * @param password 数据库解密密码
         * @return [NoteDatabase]
         */
        fun createDatabase(
            dbName: String,
            password: String? = null
        ): NoteDatabase? {
            if (TextUtils.isEmpty(dbName)) return null

            val database = Room.databaseBuilder(
                DevUtils.getContext(),
                NoteDatabase::class.java, dbName
            )
                // 允许主线程访问数据库
                .allowMainThreadQueries()
                // 构建数据库
                .build()
            return database
        }

        // ======================
        // = 获取数据库【自动缓存】 =
        // ======================

        /**
         * 获取数据库【自动缓存】
         * @return [NoteDatabase]
         */
        fun database(dbName: String = TAG): NoteDatabase? {
            return RoomManager.database(dbName, NoteDatabase::class.java)
        }
    }
}