package afkt.db.base

import androidx.multidex.MultiDexApplication
import com.therouter.TheRouter
import dev.DevUtils
import dev.engine.DevEngine
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        TheRouter.isDebug = true
        // 推荐在 Application 中初始化
        TheRouter.init(this)

        // ============
        // = DevUtils =
        // ============

        // 初始化工具类 - 可不调用, 在 DevUtils FileProviderDevApp 中已初始化, 无需主动调用
        DevUtils.init(this.applicationContext)
        // 初始化日志配置
        DevLogger.initialize(
            LogConfig().logLevel(LogLevel.DEBUG)
                .tag("AppDB_Log")
                .sortLog(true) // 美化日志, 边框包围
                .methodCount(0)
        )
        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog()
        DevUtils.openDebug()

        // ============
        // = 初始化操作 =
        // ============

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
    }
}