package afkt.umshare.base

import afkt.umshare.base.config.shareConfig
import androidx.multidex.MultiDexApplication
import com.therouter.TheRouter
import dev.DevUtils
import dev.engine.DevEngine
import dev.engine.share.DevShareEngine
import dev.umshare.UMShareEngine
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig

class BaseApplication : MultiDexApplication() {

    // 日志 TAG
    val TAG = "JPush_TAG"

    override fun onCreate() {
        super.onCreate()

        // 推荐在 Application 中初始化
        TheRouter.init(this)

        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog()
        DevUtils.openDebug()
        // 初始化 Logger 配置
        val logConfig = LogConfig.getSortLogConfig(TAG)
            .displayThreadInfo(false)
        DevLogger.initialize(logConfig)

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
        // 友盟分享 Engine 实现并初始化
        DevShareEngine.setEngine(UMShareEngine()).initialize(
            this, shareConfig
        )
    }
}