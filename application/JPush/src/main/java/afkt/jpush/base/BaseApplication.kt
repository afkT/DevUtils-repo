package afkt.jpush.base

import afkt.jpush.BuildConfig
import afkt.jpush.base.config.RouterPath
import afkt.jpush.push.JPushEngineImpl
import afkt.jpush.router.IPushCallback
import afkt.jpush.router.PUSH_KEYVALUE_ID
import afkt.jpush.router.PushRouterChecker
import afkt.jpush.ui.activity.SplashActivity
import android.app.Activity
import android.text.TextUtils
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.MMKV
import dev.DevUtils
import dev.engine.DevEngine
import dev.engine.keyvalue.DevKeyValueEngine
import dev.engine.keyvalue.MMKVConfig
import dev.engine.keyvalue.MMKVKeyValueEngineImpl
import dev.engine.push.DevPushEngine
import dev.module.push.PushConfig
import dev.utils.DevFinal
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
                    .tag("JPush_TAG")
                    .sortLog(true)
                    .methodCount(0)
            )
            // 打开 lib 内部日志 - 线上环境, 不调用方法
            DevUtils.openLog()
            DevUtils.openDebug()
        }

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)
        // MMKV Key-Value Engine 实现
        DevKeyValueEngine.setEngine(
            PUSH_KEYVALUE_ID,
            MMKVKeyValueEngineImpl(
                MMKVConfig(
                    cipher = null,
                    mmkv = MMKV.mmkvWithID(PUSH_KEYVALUE_ID, MMKV.MULTI_PROCESS_MODE)
                )
            )
        )

        // 极光推送 Engine 实现并初始化
        DevPushEngine.setEngine(JPushEngineImpl()).initialize(
            this, PushConfig(isDebugMode = BuildConfig.DEBUG)
        )

        // =============
        // = 推送路由处理 =
        // =============

        // 设置启动页、推送点击回调处理
        PushRouterChecker.setCallback(SplashActivity::class.java, object : IPushCallback {
            override fun isTriggerCallback(activityClass: String?): Boolean {
                if (TextUtils.isEmpty(activityClass)) return false

                activityClass?.let { className ->
                    if (className == SplashActivity::class.java.simpleName) {
                        return false // 属于欢迎页面则不进行处理
                    }
                }
                return true
            }

            override fun onCallback(
                activity: Activity?,
                pushData: String?
            ) {
                pushData?.let {
                    if (activity != null) {
                        ARouter.getInstance()
                            .build(RouterPath.MessageActivity_PATH)
                            .withString(DevFinal.STR.DATA, it)
                            .navigation(activity)
                    }
                }
            }
        })
    }
}