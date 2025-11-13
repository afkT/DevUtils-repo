package afkt.jpush.push.extensions

import afkt.jpush.AppRouter
import afkt.jpush.BuildConfig
import afkt.jpush.SplashActivity
import afkt.jpush.push.JPushEngineImpl
import android.app.Activity
import android.app.Application
import android.text.TextUtils
import com.tencent.mmkv.MMKV
import com.therouter.TheRouter
import dev.engine.core.keyvalue.MMKVConfig
import dev.engine.core.keyvalue.MMKVKeyValueEngineImpl
import dev.engine.keyvalue.DevKeyValueEngine
import dev.engine.push.DevPushEngine
import dev.module.push.PushConfig
import dev.utils.DevFinal

object PushHelper {

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param app [Application]
     */
    fun initialize(
        app: Application
    ) {
        // MMKV Key-Value Engine 实现
        DevKeyValueEngine.setEngine(
            PUSH_KEYVALUE_ID,
            MMKVKeyValueEngineImpl(
                MMKVConfig(
                    mmkv = MMKV.mmkvWithID(PUSH_KEYVALUE_ID, MMKV.MULTI_PROCESS_MODE)
                )
            )
        )

        // 个推推送 Engine 实现并初始化
        DevPushEngine.setEngine(JPushEngineImpl()).initialize(
            app, PushConfig(isDebugMode = BuildConfig.DEBUG)
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
                pushData?.let { data ->
                    if (activity != null) {
                        TheRouter
                            .build(AppRouter.PATH_MESSAGE_ACTIVITY)
                            .withString(DevFinal.STR.DATA, data)
                            .navigation(activity)
                    }
                }
            }
        })
    }
}