package dev.jpush

import android.content.Context
import android.content.Intent
import cn.jpush.android.api.CmdMessage
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import dev.engine.DevEngine

/**
 * detail: 极光推送广播
 * @author Ttt
 * @see https://docs.jiguang.cn/jpush/client/Android/android_api/
 */
class JPushReceiver : JPushMessageReceiver() {

    // 日志 TAG
    private val TAG = JPushReceiver::class.java.simpleName

    /**
     * 收到自定义消息回调 ( 透传 )
     * @param context Context
     * @param customMessage CustomMessage
     */
    override fun onMessage(
        context: Context,
        customMessage: CustomMessage?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onMessage] $customMessage"
        )
        customMessage?.let {
            // 透传消息送达通知
            DevEngine.getPush()?.run {
                onReceiveMessageData(
                    context, convertMessage(it)
                )
            }
        }
    }

    /**
     * 点击通知回调
     * @param context Context
     * @param message NotificationMessage
     */
    override fun onNotifyMessageOpened(
        context: Context,
        message: NotificationMessage?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onNotifyMessageOpened] $message"
        )
        message?.let {
            // 推送消息点击通知
            DevEngine.getPush()?.run {
                onNotificationMessageClicked(
                    context, convertMessage(it)
                )
            }
        }
    }

    /**
     * 收到通知回调
     * @param context Context
     * @param message NotificationMessage
     */
    override fun onNotifyMessageArrived(
        context: Context,
        message: NotificationMessage?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onNotifyMessageArrived] $message"
        )
        message?.let {
            // 推送消息送达通知
            DevEngine.getPush()?.run {
                onNotificationMessageArrived(
                    context, convertMessage(it)
                )
            }
        }
    }

    /**
     * 清除通知回调
     * @param context Context
     * @param message NotificationMessage
     */
    override fun onNotifyMessageDismiss(
        context: Context,
        message: NotificationMessage?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onNotifyMessageDismiss] $message"
        )
    }

    /**
     * 通知的 MultiAction 回调
     * @param context Context
     * @param intent Intent
     */
    override fun onMultiActionClicked(
        context: Context,
        intent: Intent?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onMultiActionClicked] 用户点击了通知栏按钮"
        )
    }

    /**
     * 注册成功回调
     * @param context Context
     * @param registrationId String
     */
    override fun onRegister(
        context: Context,
        registrationId: String?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onRegister] $registrationId"
        )
        registrationId?.let {
            // 初始化 Client Id 成功通知
            DevEngine.getPush()?.onReceiveClientId(
                context, it
            )
        }
    }

    /**
     * 长连接状态回调
     * @param context Context
     * @param isConnected Boolean
     */
    override fun onConnected(
        context: Context,
        isConnected: Boolean
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onConnected] $isConnected"
        )
        // 在线状态变化通知
        DevEngine.getPush()?.onReceiveOnlineState(
            context, isConnected
        )
    }

    /**
     * 交互事件回调
     * @param context Context
     * @param cmdMessage CmdMessage
     */
    override fun onCommandResult(
        context: Context,
        cmdMessage: CmdMessage?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onCommandResult] $cmdMessage"
        )
        cmdMessage?.let {
            // 厂商 token 注册回调
            if (it.cmd == 10000 && it.extra != null) {
                val token = it.extra.getString("token")
                token?.let {
                    // 设备 ( 厂商 ) Token 通知
                    DevEngine.getPush()?.onReceiveDeviceToken(
                        context, token
                    )
                }
                DevEngine.getLog()?.dTag(
                    TAG, "[onCommandResult] token : $token"
                )
            } else {
                // 命令回执通知
                DevEngine.getPush()?.run {
                    onReceiveCommandResult(
                        context, convertMessage(it)
                    )
                }
            }
        }
    }

    /**
     * 通知开关状态回调
     * @param context Context
     * @param isOn Boolean
     * @param source Int
     */
    override fun onNotificationSettingsCheck(
        context: Context,
        isOn: Boolean,
        source: Int
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onNotificationSettingsCheck] isOn : $isOn, source: $source"
        )
    }
}