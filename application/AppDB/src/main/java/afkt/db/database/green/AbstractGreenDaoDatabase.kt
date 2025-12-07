package afkt.db.database.green

import gen.greendao.DaoMaster
import gen.greendao.DaoSession
import org.greenrobot.greendao.database.Database

/**
 * detail: GreenDao Database 抽象类
 * @author Ttt
 */
abstract class AbstractGreenDaoDatabase {

    companion object {

        // 数据库名
        private const val DATABASE_NAME = "greendao-db"

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
         * @param clazz    [AbstractGreenDaoDatabase] 实现类
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
         * @param clazz    [AbstractGreenDaoDatabase] 实现类
         * @return [AbstractGreenDaoDatabase]
         */
        fun create(
            dbName: String,
            password: String?,
            clazz: Class<*>
        ): AbstractGreenDaoDatabase?
    }

    // ============
    // = abstract =
    // ============

    // =
    /**
     * 获取 DaoMaster.OpenHelper
     * @return [DaoMaster.OpenHelper]
     */
    abstract fun getHelper(): DaoMaster.OpenHelper?

    /**
     * 获取 Database
     * @return [Database]
     */
    abstract fun getDatabase(): Database?

    /**
     * 获取 DaoMaster
     * @return [DaoMaster]
     */
    abstract fun getDaoMaster(): DaoMaster?

    /**
     * 获取 DaoSession
     * @return [DaoSession]
     */
    abstract fun getDaoSession(): DaoSession?
}