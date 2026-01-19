package afkt.jpush.base

import afkt.jpush.push.extensions.PushHelper
import androidx.multidex.MultiDexApplication
import com.therouter.TheRouter
import dev.DevUtils
import dev.engine.DevEngine

class BaseApplication : MultiDexApplication() {

    // 日志 TAG
    val TAG = "JPush_TAG"

    override fun onCreate() {
        super.onCreate()

        // Router 初始化
        TheRouter.init(this)

        // DevUtils Debug 开发配置
        DevUtils.debugDevelop(TAG)

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)

        // Push 初始化方法
        PushHelper.initialize(this)
    }
}