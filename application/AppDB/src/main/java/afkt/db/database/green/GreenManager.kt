package afkt.db.database.green

import afkt.db.database.green.module.image.ImageDatabase
import afkt.db.database.green.module.note.NoteDatabase
import dev.engine.extensions.log.log_eTag
import dev.engine.extensions.log.log_isPrintLog
import dev.utils.common.StringUtils

/**
 * detail: GreenDao 数据库管理类
 * @author Ttt
 */
object GreenManager {

    // 日志 TAG
    val TAG: String = GreenManager::class.java.getSimpleName()

    // Database 对象缓存
    private val sDatabaseMaps = mutableMapOf<String, AbstractGreenDaoDatabase?>()

    // ============
    // = database =
    // ============

    /**
     * 获取 GreenDao Database 对象
     * @param dbName 数据库名
     * @param clazz  [AbstractGreenDaoDatabase] 实现类
     * @return [AbstractGreenDaoDatabase]
     */
    fun <T : AbstractGreenDaoDatabase> database(
        dbName: String,
        clazz: Class<*>
    ): T? {
        return database(dbName, null, clazz)
    }


    /**
     * 获取 GreenDao Database 对象
     * @param dbName   数据库名
     * @param password 数据库解密密码
     * @param clazz    [AbstractGreenDaoDatabase] 实现类
     * @return [AbstractGreenDaoDatabase]
     */
    fun <T : AbstractGreenDaoDatabase> database(
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
    private val CREATE = object : AbstractGreenDaoDatabase.Creator {
        override fun getDatabaseName(
            dbName: String,
            password: String?,
            clazz: Class<*>
        ): String {
            return AbstractGreenDaoDatabase.createDatabaseName(
                dbName,
                StringUtils.isNotEmpty(password)
            )
        }

        override fun create(
            dbName: String,
            password: String?,
            clazz: Class<*>
        ): AbstractGreenDaoDatabase? {
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