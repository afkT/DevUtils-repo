package afkt.gtpush.push

import afkt.gtpush.push.extensions.PushTransferRouter
import android.app.Application
import android.content.Context
import com.igexin.sdk.PushManager
import dev.engine.push.IPushEngine
import dev.module.push.PushConfig
import dev.module.push.PushMessage

/**
 * detail: 个推推送 Engine 实现
 * @author Ttt
 */
class GTPushEngineImpl : IPushEngine<PushConfig, PushMessage> {

    override fun initialize(
        application: Application?,
        config: PushConfig?
    ) {
        application?.let {
            // 推送初始化
            PushManager.getInstance().initialize(it)
        }
    }

    override fun register(
        context: Context?,
        config: PushConfig?
    ) {
    }

    override fun unregister(
        context: Context?,
        config: PushConfig?
    ) {
    }

    override fun onReceiveServicePid(
        context: Context?,
        pid: Int
    ) {
    }

    override fun onReceiveClientId(
        context: Context?,
        clientId: String?
    ) {
    }

    override fun onReceiveDeviceToken(
        context: Context?,
        deviceToken: String?
    ) {
    }

    override fun onReceiveOnlineState(
        context: Context?,
        online: Boolean
    ) {
    }

    override fun onReceiveCommandResult(
        context: Context?,
        message: PushMessage?
    ) {
    }

    override fun onNotificationMessageArrived(
        context: Context?,
        message: PushMessage?
    ) {
    }

    override fun onNotificationMessageClicked(
        context: Context?,
        message: PushMessage?
    ) {
        // 推送消息点击通知
        message?.let {
            PushTransferRouter.start(context, it)
        }
    }

    override fun onReceiveMessageData(
        context: Context?,
        message: PushMessage?
    ) {
        // 透传消息送达通知
    }

    override fun convertMessage(message: Any?): PushMessage? {
        return convertEngineMessage(message)
    }
}