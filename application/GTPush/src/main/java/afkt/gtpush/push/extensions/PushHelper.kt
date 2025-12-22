package afkt.gtpush.push.extensions

import afkt.gtpush.AppRouter
import afkt.gtpush.BuildConfig
import afkt.gtpush.SplashActivity
import afkt.gtpush.push.GTPushEngineImpl
import afkt.jpush.push.extensions.IPushCallback
import afkt.jpush.push.extensions.PushTransferHandler
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.text.TextUtils
import dev.engine.extensions.json.fromJson
import dev.engine.extensions.json.toJson
import dev.engine.push.DevPushEngine
import dev.module.push.PushConfig
import dev.module.push.PushMessage
import dev.simple.extensions.hi.hiif.hiIfNotNull

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
        // 推送消息中转处理 ( 初始化注册方法 )
        PushTransferHandler.register(app)

        // 个推推送 Engine 实现并初始化
        DevPushEngine.setEngine(GTPushEngineImpl()).initialize(
            app, PushConfig(isDebugMode = BuildConfig.DEBUG)
        )

        // =============
        // = 推送路由处理 =
        // =============

        // 设置推送回调处理
        PushTransferHandler.setPushCallback(object : IPushCallback {
            override fun isTriggerCallback(
                activity: Activity,
                activityClass: String
            ): Boolean {
                if (TextUtils.isEmpty(activityClass)) return false

                // 属于欢迎页面则不进行处理
                if (activityClass == SplashActivity::class.java.simpleName) {
                    return false
                }
//                // 属于 xxx 页面则不进行处理
//                if (activityClass == XXXXActivity::class.java.simpleName) {
//                    return false
//                }
                return true
            }

            override fun onCallback(
                activity: Activity,
                pushMessage: String?
            ) {
                pushMessage?.let { data ->
                    data.fromJson(
                        classOfT = PushRouterInfo::class.java
                    ).hiIfNotNull { routerInfo ->
                        // 处理消息跳转
                        handleMessageNavigation(activity, routerInfo, true)
                    }
                }
            }
        })
    }

    // =============
    // = 点击推送消息 =
    // =============

    /**
     * 处理推送消息【点击】
     * @param context [Context]
     * @param message 推送数据
     */
    fun handlePushClick(
        context: Context,
        message: PushMessage
    ) {
        // 转换消息实体类【路由信息】
        val routerInfo = message.toRouterInfo()
        // 应用前后台校验
        if (isAppInForeground(context)) {
            // APP 在前台或后台 ( 已启动 )

            handleMessageNavigation(context, routerInfo, true)
        } else {
            // APP 未启动

            // 保存推送数据 ( 用于 APP 未启动的情况 )
            PushTransferHandler.savePendingMessage(
                routerInfo.toJson()
            )
            handleMessageNavigation(context, routerInfo, false)
        }
    }

    /**
     * 处理推送消息【透传】
     * @param context [Context]
     * @param message 推送数据
     */
    fun handlePushMessage(
        context: Context,
        message: PushMessage
    ) {
//        // 转换消息实体类【路由信息】
//        val routerInfo = message.toRouterInfo()
//        // 处理接收到的消息 ( 非点击通知栏 )
//        if (isAppInForeground(context)) {
//            // APP 在前台或后台 ( 已启动 )
//            // ...
//        } else {
//            // APP 未启动
//            // ...
//        }
    }

    /**
     * 处理消息跳转
     * @param context [Context]
     * @param routerInfo 推送路由信息
     * @param isAppStarted App 是否已启动
     */
    fun handleMessageNavigation(
        context: Context,
        routerInfo: PushRouterInfo,
        isAppStarted: Boolean = false
    ) {
        if (isAppStarted) {
            //【APP 已启动】根据路由信息跳转具体页面
            val content = routerInfo.content
            if (content != null) {
                // 模拟根据路由信息跳转不同页面
                AppRouter.routerMessageActivity(content)
            }
        } else {
            //【APP 未启动】先启动 APP，后续会自动进行处理

        }
    }

    // ===============
    // = 应用前后台校验 =
    // ===============

    private fun isAppInForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses ?: return false

        val packageName = context.packageName
        for (appProcess in runningAppProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                appProcess.processName == packageName
            ) {
                return true
            }
        }
        return false
    }
}

// ==========
// = 消息转换 =
// ==========

/**
 * detail: 转换消息实体类【路由信息】
 * @author Ttt
 */
fun PushMessage.toRouterInfo(): PushRouterInfo {
    /**
     * 实际项目中读取某些字段组装成实体类
     * 并根据应用前后台校验
     * 1.直接进行跳转【应用启动】
     * 2.转 JSON 启动应用，跳转路由【应用未启动】
     */
    return PushRouterInfo(this.content)
}

/**
 * detail: 推送路由信息
 * @author Ttt
 * 【模拟】推送消息提取成路由信息实体类
 */
class PushRouterInfo(
    val content: String? = "",
)