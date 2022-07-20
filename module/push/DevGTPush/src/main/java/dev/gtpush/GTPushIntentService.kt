package dev.gtpush

import android.content.Context
import com.igexin.sdk.GTIntentService
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage
import dev.engine.DevEngine

/**
 * detail: 个推推送广播
 * @author Ttt
 * @see https://docs.getui.com/getui/mobile/android/api/
 */
class GTPushIntentService : GTIntentService() {

    // 日志 TAG
    private val TAG = GTPushIntentService::class.java.simpleName

    /**
     * 推送进程启动通知
     * @param context Context
     * @param pid Int
     */
    override fun onReceiveServicePid(
        context: Context?,
        pid: Int
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onReceiveServicePid] $pid"
        )
        // 推送进程启动通知
        DevEngine.getPush()?.onReceiveServicePid(
            context, pid
        )
    }

    /**
     * 初始化 Client Id 成功通知
     * @param context Context
     * @param clientId String
     */
    override fun onReceiveClientId(
        context: Context?,
        clientId: String?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onReceiveClientId] $clientId"
        )
        clientId?.let {
            // 初始化 Client Id 成功通知
            DevEngine.getPush()?.onReceiveClientId(
                context, it
            )
        }
    }

    /**
     * 设备 ( 厂商 ) Token 通知
     * @param context Context
     * @param deviceToken String
     */
    override fun onReceiveDeviceToken(
        context: Context?,
        deviceToken: String?
    ) {
        super.onReceiveDeviceToken(context, deviceToken)

        DevEngine.getLog()?.dTag(
            TAG, "[onReceiveDeviceToken] $deviceToken"
        )
        deviceToken?.let {
            // 设备 ( 厂商 ) Token 通知
            DevEngine.getPush()?.onReceiveDeviceToken(
                context, it
            )
        }
    }

    /**
     * 在线状态变化通知
     * @param context Context
     * @param online Boolean
     */
    override fun onReceiveOnlineState(
        context: Context?,
        online: Boolean
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onReceiveOnlineState] $online"
        )
        // 在线状态变化通知
        DevEngine.getPush()?.onReceiveOnlineState(
            context, online
        )
    }

    /**
     * 命令回执通知
     * @param context Context
     * @param message GTCmdMessage
     */
    override fun onReceiveCommandResult(
        context: Context?,
        message: GTCmdMessage?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onReceiveCommandResult] $message"
        )
        message?.let {
            // 命令回执通知
            DevEngine.getPush()?.run {
                onReceiveCommandResult(
                    context, convertMessage(it)
                )
            }
        }
    }

    /**
     * 推送消息送达通知
     * @param context Context
     * @param message GTNotificationMessage
     */
    override fun onNotificationMessageArrived(
        context: Context?,
        message: GTNotificationMessage?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onNotificationMessageArrived] $message"
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
     * 推送消息点击通知
     * @param context Context
     * @param message GTNotificationMessage
     */
    override fun onNotificationMessageClicked(
        context: Context?,
        message: GTNotificationMessage?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onNotificationMessageClicked] $message"
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
     * 透传消息送达通知
     * @param context Context
     * @param message GTTransmitMessage
     */
    override fun onReceiveMessageData(
        context: Context?,
        message: GTTransmitMessage?
    ) {
        DevEngine.getLog()?.dTag(
            TAG, "[onReceiveMessageData] $message"
        )
        message?.let {
            // 推送消息点击通知
            DevEngine.getPush()?.run {
                onReceiveMessageData(
                    context, convertMessage(it)
                )
            }
        }
    }
}