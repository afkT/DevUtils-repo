package afkt.umshare.base

import afkt.umshare.base.config.shareConfig
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import dev.DevUtils
import dev.engine.DevEngine
import dev.engine.share.DevShareEngine
import dev.umshare.UMShareEngine
import dev.utils.BuildConfig
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel

class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
            // 打印日志的时候打印线程堆栈
            ARouter.printStackTrace()
        }
        // 尽可能早, 推荐在 Application 中初始化
        ARouter.init(this)

        if (BuildConfig.DEBUG) {
            // 初始化 Logger 配置
            DevLogger.initialize(
                LogConfig()
                    .logLevel(LogLevel.DEBUG)
                    .tag("UMShare_TAG")
                    .sortLog(true)
                    .methodCount(0)
            )
            // 打开 lib 内部日志 - 线上环境, 不调用方法
            DevUtils.openLog()
            DevUtils.openDebug()
        }

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
        // 友盟分享 Engine 实现并初始化
        DevShareEngine.setEngine(UMShareEngine()).initialize(
            this, shareConfig
        )
    }
}