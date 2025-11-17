package afkt.db.database.room

import afkt.db.database.room.module.image.ImageDatabase
import afkt.db.database.room.module.note.NoteDatabase
import dev.engine.extensions.log.log_eTag
import dev.engine.extensions.log.log_isPrintLog
import dev.utils.common.StringUtils

/**
 * detail: Room 数据库管理类
 * @author Ttt
 */
object RoomManager {

    // 日志 TAG
    val TAG: String = RoomManager::class.java.getSimpleName()

    // Database 对象缓存
    private val sDatabaseMaps = mutableMapOf<String, AbstractRoomDatabase?>()

    // ============
    // = database =
    // ============

    /**
     * 获取 RoomDatabase 对象
     * @param dbName 数据库名
     * @param clazz  [AbstractRoomDatabase] 实现类
     * @return [AbstractRoomDatabase]
     */
    fun <T : AbstractRoomDatabase> database(
        dbName: String,
        clazz: Class<*>
    ): T? {
        return database(dbName, null, clazz)
    }

    /**
     * 获取 RoomDatabase 对象
     * @param dbName   数据库名
     * @param password 数据库解密密码
     * @param clazz    [AbstractRoomDatabase] 实现类
     * @return [AbstractRoomDatabase]
     */
    fun <T : AbstractRoomDatabase> database(
        dbName: String,
        password: String?,
        clazz: Class<*>
    ): T? {
        if (StringUtils.isEmpty(dbName)) return null

        // 获取数据库名
        val databaseName = CREATE.getDatabaseName(dbName, password, clazz)
        // 获取数据库
        var database = sDatabaseMaps[databaseName]
        if (database == null) {
            try {
                database = CREATE.create(dbName, password, clazz)
                sDatabaseMaps[databaseName] = database
            } catch (e: Exception) {
                if (log_isPrintLog()) {
                    TAG.log_eTag(message = "database", throwable = e)
                }
            }
        }
        if (database != null) {
            try {
                return database as T
            } catch (e: Exception) {
                if (log_isPrintLog()) {
                    TAG.log_eTag(message = "database convert T", throwable = e)
                }
            }
            return null
        }
        return null
    }

    // =======
    // = 创建 =
    // =======

    // 数据库创建接口
    private val CREATE = object : AbstractRoomDatabase.Creator {
        override fun getDatabaseName(
            dbName: String,
            password: String?,
            clazz: Class<*>
        ): String {
            return AbstractRoomDatabase.createDatabaseName(dbName, StringUtils.isNotEmpty(password))
        }

        override fun create(
            dbName: String,
            password: String?,
            clazz: Class<*>
        ): AbstractRoomDatabase? {
            if (clazz == NoteDatabase::class.java) {
                return NoteDatabase.createDatabase(dbName, password)
            } else if (clazz == ImageDatabase::class.java) {
                // 不同模块数据库实现【演示类】
                return ImageDatabase.createDatabase(dbName, password)
            }
            return null
        }
    }
}