package afkt.db.database.room

import androidx.room.RoomDatabase

/**
 * detail: Room Database 抽象类
 * @author Ttt
 */
abstract class AbstractRoomDatabase : RoomDatabase() {

    companion object {

        // 数据库名
        private const val DATABASE_NAME = "room-db"

        /**
         * 拼接数据库名
         * @param dbName    数据库名
         * @param encrypted 数据库是否加密
         * @return 数据库名
         */
        fun createDatabaseName(
            dbName: String,
            encrypted: Boolean
        ): String {
            if (encrypted) return "$dbName-$DATABASE_NAME-encrypted"
            return "$dbName-$DATABASE_NAME"
        }
    }

    // ============
    // = 创建数据库 =
    // ============

    /**
     * detail: 数据库创建接口
     * @author Ttt
     */
    interface Creator {

        /**
         * 获取数据库名
         * @param dbName   数据库名
         * @param password 数据库解密密码
         * @param clazz    [AbstractRoomDatabase] 实现类
         * @return 数据库名
         */
        fun getDatabaseName(
            dbName: String,
            password: String?,
            clazz: Class<*>
        ): String

        /**
         * 创建数据库方法
         * @param dbName   数据库名
         * @param password 数据库解密密码
         * @param clazz    [AbstractRoomDatabase] 实现类
         * @return [AbstractRoomDatabase]
         */
        fun create(
            dbName: String,
            password: String?,
            clazz: Class<*>
        ): AbstractRoomDatabase?
    }
}