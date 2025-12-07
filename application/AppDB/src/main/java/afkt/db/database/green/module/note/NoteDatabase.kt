package afkt.db.database.green.module.note

import afkt.db.database.green.AbstractGreenDaoDatabase
import afkt.db.database.green.GreenManager
import afkt.db.database.green.MigrationHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.text.TextUtils
import dev.DevUtils
import dev.engine.extensions.log.log_dTag
import dev.utils.common.StringUtils
import gen.greendao.DaoMaster
import gen.greendao.DaoSession
import gen.greendao.NoteDao
import gen.greendao.NotePictureDao
import org.greenrobot.greendao.database.Database

/**
 * detail: Note 数据库
 * @author Ttt
 */
class NoteDatabase(
    // DaoMaster.OpenHelper
    private val mHelper: DaoMaster.OpenHelper,
    // Database
    private val mDatabase: Database,
    // DaoMaster
    private val mDaoMaster: DaoMaster,
    // DaoSession
    private val mDaoSession: DaoSession
) : AbstractGreenDaoDatabase() {

    // =======
    // = Dao =
    // =======

    fun noteDao(): NoteDao {
        return mDaoSession.noteDao
    }

    fun notePictureDao(): NotePictureDao {
        return mDaoSession.notePictureDao
    }

    // =========================
    // = AbstractGreenDatabase =
    // =========================

    override fun getHelper(): DaoMaster.OpenHelper {
        return mHelper
    }

    override fun getDatabase(): Database {
        return mDatabase
    }

    override fun getDaoMaster(): DaoMaster {
        return mDaoMaster
    }

    override fun getDaoSession(): DaoSession {
        return mDaoSession
    }

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

            // DBHelper
            val helper = UpgradeHelper(
                DevUtils.getContext(), createDatabaseName(
                    dbName, StringUtils.isNotEmpty(password)
                )
            )
            val database = if (TextUtils.isEmpty(password)) {
                // regular SQLite database
                helper.writableDb
            } else {
                // encrypted SQLCipher database
                helper.getEncryptedWritableDb(password)
            }
            val daoMaster = DaoMaster(database)
            val daoSession = daoMaster.newSession()
            return NoteDatabase(
                helper, database,
                daoMaster, daoSession
            )
        }

        // ======================
        // = 获取数据库【自动缓存】 =
        // ======================

        /**
         * 获取数据库【自动缓存】
         * @return [NoteDatabase]
         */
        fun database(dbName: String = TAG): NoteDatabase? {
            return GreenManager.database(dbName, NoteDatabase::class.java)
        }
    }

    // ============
    // = 数据库升级 =
    // ============

    /**
     * detail: DB 升级 Helper
     * 默认的 DaoMaster.DevOpenHelper 会在数据库升级时, 删除所有的表, 意味着这将导致数据的丢失
     * 所以, 在正式的项目中, 你还应该做一层封装, 来实现数据库的安全升级
     */
    private class UpgradeHelper : DaoMaster.OpenHelper {

        constructor(
            context: Context?,
            name: String?
        ) : super(context, name)

        constructor(
            context: Context?,
            name: String?,
            factory: CursorFactory?
        ) : super(context, name, factory)

        override fun onUpgrade(
            db: SQLiteDatabase?,
            oldVersion: Int,
            newVersion: Int
        ) {
            TAG.log_dTag(
                null, "oldVersion: %s, newVersion: %s",
                oldVersion, newVersion
            )
            MigrationHelper.migrate(
                db, NoteDao::class.java,
                NotePictureDao::class.java
            )
        }
    }
}