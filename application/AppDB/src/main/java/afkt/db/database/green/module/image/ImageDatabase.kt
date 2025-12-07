package afkt.db.database.green.module.image

import afkt.db.database.green.AbstractGreenDaoDatabase
import afkt.db.database.green.GreenManager
import afkt.db.database.green.module.note.NoteDatabase
import gen.greendao.DaoMaster
import gen.greendao.DaoSession
import org.greenrobot.greendao.database.Database

/**
 * detail: 不同模块数据库实现【演示类】
 * @author Ttt
 * 主要演示区分 note 模块包目录
 * 参考 [NoteDatabase]
 */
class ImageDatabase : AbstractGreenDaoDatabase() {

    // =========================
    // = AbstractGreenDatabase =
    // =========================

    override fun getHelper(): DaoMaster.OpenHelper? {
        return null
    }

    override fun getDatabase(): Database? {
        return null
    }

    override fun getDaoMaster(): DaoMaster? {
        return null
    }

    override fun getDaoSession(): DaoSession? {
        return null
    }

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
            return null
        }

        // ======================
        // = 获取数据库【自动缓存】 =
        // ======================

        /**
         * 获取数据库【自动缓存】
         * @return [ImageDatabase]
         */
        fun database(dbName: String = TAG): ImageDatabase? {
            return GreenManager.database(dbName, ImageDatabase::class.java)
        }
    }
}