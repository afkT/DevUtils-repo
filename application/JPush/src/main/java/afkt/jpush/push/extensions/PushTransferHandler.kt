package afkt.jpush.push.extensions

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.tencent.mmkv.MMKV
import dev.engine.DevEngine
import dev.engine.core.keyvalue.MMKVConfig
import dev.engine.core.keyvalue.MMKVKeyValueEngineImpl
import dev.engine.keyvalue.DevKeyValueEngine
import dev.engine.keyvalue.IKeyValueEngine

/**
 * detail: 推送消息中转处理
 * @author Ttt
 */
class PushTransferHandler private constructor() {

    companion object {

        val instance: PushTransferHandler by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PushTransferHandler()
        }

        /**
         * 初始化注册方法
         * @param app [Application]
         * @param pushCallback 推送回调接口
         */
        fun register(
            app: Application,
            pushCallback: IPushCallback? = null
        ) {
            instance.register(app, pushCallback)
        }

        /**
         * 设置推送回调接口
         * @param pushCallback 推送回调接口
         */
        fun setPushCallback(pushCallback: IPushCallback?) {
            instance.setPushCallback(pushCallback)
        }

        /**
         * 保存推送数据 ( 用于 APP 未启动的情况 )
         * @param message 推送数据 JSON 格式
         */
        fun savePendingMessage(message: String?) {
            if (message != null && message.isNotEmpty()) {
                PTHDataManager.savePendingMessage(message)
            }
        }

        /**
         * 重置推送数据
         */
        fun resetMessage() {
            PTHDataManager.resetMessage()
        }
    }

    // =========
    // = 初始化 =
    // =========

    /**
     * 初始化注册方法
     * @param app [Application]
     * @param pushCallback 推送回调接口
     */
    private fun register(
        app: Application,
        pushCallback: IPushCallback? = null
    ) {
        // 设置推送回调接口
        if (pushCallback != null) {
            setPushCallback(pushCallback)
        }
        try {
            app.unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE)
        } catch (_: Exception) {
        }
        // 初始化 DataManager
        PTHDataManager.initialize()
        // 监听 Activity 生命周期
        app.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE)
    }

    // ====================
    // = ActivityLifecycle =
    // ====================

    // ActivityLifecycleCallbacks 实现类 ( 监听 Activity 生命周期 )
    private val ACTIVITY_LIFECYCLE = object : PushActivityLifecycle() {
        override fun onActivityResumed(activity: Activity) {
            // 检查推送路由
            checker(activity)
        }
    }

    // ================
    // = IPushCallback =
    // ================

    // 推送回调接口
    private var _pushCallback: IPushCallback? = null

    private fun setPushCallback(pushCallback: IPushCallback?) {
        _pushCallback = pushCallback
    }

    /**
     * 检查推送路由
     * @param activity [Activity]
     */
    private fun checker(activity: Activity) {
        _pushCallback?.run {
            val activityClass = activity.javaClass.simpleName
            if (isTriggerCallback(activity, activityClass)) {
                // 是否点击通知栏推送消息 ( 用于判断是否处理推送路由 )
                if (PTHDataManager.isClickMessage()) {
                    val pushData = PTHDataManager.getPendingMessage()
                    // 触发回调
                    onCallback(activity, pushData)
                }
            }
        }
    }
}

/**
 * detail: 推送回调接口
 * @author Ttt
 */
interface IPushCallback {

    /**
     * 是否触发回调
     * @param activity [Activity]
     * @param activityClass Activity 类名
     * @return `true` yes, `false` no
     */
    fun isTriggerCallback(
        activity: Activity,
        activityClass: String
    ): Boolean

    /**
     * 推送回调方法
     * @param activity [Activity]
     * @param pushMessage 推送数据
     */
    fun onCallback(
        activity: Activity,
        pushMessage: String?
    )
}

/**
 * detail: 推送数据管理类
 * @author Ttt
 * [PTHDataManager] => Push Transfer Handler DataManager
 */
private object PTHDataManager {

    // 是否点击通知栏推送消息 ( 用于判断是否处理推送路由 )
    private const val KEY_CLICK_MESSAGE = "isClickMessage"

    // 推送数据 ( JSON 格式 )
    private const val KEY_PUSH_MESSAGE = "pushMessage"

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否点击通知栏推送消息 ( 用于判断是否处理推送路由 )
     */
    fun isClickMessage(): Boolean {
        return engine()?.getBoolean(KEY_CLICK_MESSAGE) ?: false
    }

    /**
     * 保存推送数据 ( 用于 APP 未启动的情况 )
     * @param message 推送数据 JSON 格式
     */
    fun savePendingMessage(
        message: String
    ) {
        engine()?.let {
            // 表示需要处理推送消息
            it.putBoolean(KEY_CLICK_MESSAGE, true)
            // 保存推送数据
            it.putString(KEY_PUSH_MESSAGE, message)
        }
    }

    /**
     * 获取推送数据【并清空待处理数据】
     */
    fun getPendingMessage(): String? {
        return engine()?.let {
            val pushMessage = it.getString(KEY_PUSH_MESSAGE)
            // 移除旧数据
            it.clear()
            return@let pushMessage
        }
    }

    /**
     * 重置推送数据
     */
    fun resetMessage() {
        engine()?.clear()
    }

    // =============
    // = Key Value =
    // =============

    private const val PUSH_KEY_VALUE_ENGINE = "PTHKVEngine"

    private fun engine(): IKeyValueEngine<*>? {
        return DevEngine.getKeyValue(
            PUSH_KEY_VALUE_ENGINE
        )
    }

    fun initialize() {
        // MMKV Key-Value Engine 实现
        DevKeyValueEngine.setEngine(
            PUSH_KEY_VALUE_ENGINE, MMKVKeyValueEngineImpl(
                MMKVConfig(
                    mmkv = MMKV.mmkvWithID(
                        PUSH_KEY_VALUE_ENGINE,
                        MMKV.MULTI_PROCESS_MODE
                    )
                )
            )
        )
    }
}

/**
 * detail: ActivityLifecycleCallbacks 实现类 ( 监听 Activity 生命周期 )
 * @author Ttt
 */
private abstract class PushActivityLifecycle : ActivityLifecycleCallbacks {

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
    ) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle
    ) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }
}