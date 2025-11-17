package afkt.db.database.room.module.image

import afkt.db.database.room.AbstractRoomDatabase
import afkt.db.database.room.RoomManager
import afkt.db.database.room.module.note.NoteDatabase
import android.text.TextUtils
import androidx.room.Room
import dev.DevUtils

/**
 * detail: 不同模块数据库实现【演示类】
 * @author Ttt
 * 主要演示区分 note 模块包目录
 * 参考 [NoteDatabase]
 */
abstract class ImageDatabase : AbstractRoomDatabase() {

    // =======
    // = Dao =
    // =======

    // ...

    // ============
    // = database =
    // ============

    companion object {

        // 日志 TAG
        val TAG: String = ImageDatabase::class.java.getSimpleName()

        /**
         * 创建数据库
         * @param dbName 数据库名
         * @param password 数据库解密密码
         * @return [ImageDatabase]
         */
        fun createDatabase(
            dbName: String,
            password: String? = null
        ): ImageDatabase? {
            if (TextUtils.isEmpty(dbName)) return null

            val database = Room.databaseBuilder(
                DevUtils.getContext(),
                ImageDatabase::class.java, dbName
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
         * @return [ImageDatabase]
         */
        fun database(dbName: String = TAG): ImageDatabase? {
            return RoomManager.database(dbName, ImageDatabase::class.java)
        }
    }
}