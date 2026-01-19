package afkt.umshare.base

import androidx.multidex.MultiDexApplication
import com.therouter.TheRouter
import dev.DevUtils
import dev.engine.DevEngine
import dev.engine.share.DevShareEngine
import dev.module.share.ShareConfig
import dev.umshare.UMShareEngine

class BaseApplication : MultiDexApplication() {

    // 日志 TAG
    val TAG = "UMShare_TAG"

    override fun onCreate() {
        super.onCreate()

        // Router 初始化
        TheRouter.init(this)

        // DevUtils Debug 开发配置
        DevUtils.debugDevelop(TAG)

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)

        // 友盟分享 Engine 实现并初始化
        DevShareEngine.setEngine(UMShareEngine()).initialize(
            this, shareConfig
        )
    }
}

// 分享配置信息
val shareConfig: ShareConfig by lazy {
    ShareConfig("", "", platformKey = ArrayList())
}