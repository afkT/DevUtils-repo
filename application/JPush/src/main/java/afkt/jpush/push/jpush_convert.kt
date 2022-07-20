package afkt.jpush.push

import cn.jpush.android.api.CmdMessage
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.NotificationMessage
import dev.jpush.getPushSDKName
import dev.jpush.toJPushThirdMessage
import dev.module.push.PushMessage
import org.json.JSONObject

// =============
// = 对外公开方法 =
// =============

fun convertEngineMessage(message: Any?): PushMessage? {
    return message?.let {
        return when (it) {
            is CmdMessage -> {
                convertCmdMessage(it)
            }
            is NotificationMessage -> {
                convertNotificationMessage(it)
            }
            is CustomMessage -> {
                convertCustomMessage(it)
            }
            is JSONObject -> {
                convertJSONObject(it)
            }
            else -> null
        }
    }
}

// ==========
// = 内部方法 =
// ==========

private fun convertCmdMessage(message: CmdMessage): PushMessage {
    return PushMessage(
        cmd = message.cmd.toString(),
        extrasBundle = message.extra,
        errorCode = message.errorCode.toString(),
        msg = message.msg
    )
}

private fun convertNotificationMessage(message: NotificationMessage): PushMessage {
    return PushMessage(
        messageId = message.msgId,
        title = message.notificationTitle,
        content = message.notificationContent,
        extras = message.notificationExtras,
        whichPushSDK = message.platform,
        whichPushSDKName = getPushSDKName(message.platform.toByte()),
    )
}

private fun convertCustomMessage(message: CustomMessage): PushMessage {
    return PushMessage(
        messageId = message.messageId,
        title = message.title,
        content = message.message,
        extras = message.extra,
        whichPushSDK = message.platform.toInt(),
        whichPushSDKName = getPushSDKName(message.platform),
    )
}

private fun convertJSONObject(jsonObject: JSONObject): PushMessage {
    val message = toJPushThirdMessage(jsonObject)
    return PushMessage(
        messageId = message.msgId,
        title = message.title,
        content = message.content,
        extras = message.extras,
        whichPushSDK = message.whichPushSDK.toInt(),
        whichPushSDKName = message.whichPushSDKName,
    )
}