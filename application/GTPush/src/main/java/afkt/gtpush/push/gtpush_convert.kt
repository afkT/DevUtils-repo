package afkt.gtpush.push

import com.igexin.sdk.PushConsts
import com.igexin.sdk.message.BindAliasCmdMessage
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage
import dev.module.push.PushMessage
import dev.utils.common.ConvertUtils

// =============
// = 对外公开方法 =
// =============

fun convertEngineMessage(message: Any?): PushMessage? {
    return message?.let {
        return when (it) {
            is GTCmdMessage -> {
                convertGTCmdMessage(it)
            }
            is GTNotificationMessage -> {
                convertGTNotificationMessage(it)
            }
            is GTTransmitMessage -> {
                convertGTTransmitMessage(it)
            }
            else -> null
        }
    }
}

// ==========
// = 内部方法 =
// ==========

private fun convertGTCmdMessage(message: GTCmdMessage): PushMessage {
    var code = ""
    if (message.action == PushConsts.BIND_ALIAS_RESULT) {
        if (message is BindAliasCmdMessage) {
//            code 结果说明
//            0: 成功
//            10099: SDK 未初始化成功
//            30001: 绑定别名失败, 频率过快, 两次调用的间隔需大于 5 s
//            30002: 绑定别名失败, 参数错误
//            30003: 绑定别名请求被过滤
//            30004: 绑定别名失败, 未知异常
//            30005: 绑定别名时, cid 未获取到
//            30006: 绑定别名时, 发生网络错误
//            30007: 别名无效
//            30008: sn 无效
            code = message.code
            val sn = message.sn
        }
    }
    return PushMessage(
        code = code
    )
}

private fun convertGTNotificationMessage(message: GTNotificationMessage): PushMessage {
    return PushMessage(
        messageId = message.messageId,
        title = message.title,
        content = message.content,
    )
}

private fun convertGTTransmitMessage(message: GTTransmitMessage): PushMessage {
    return PushMessage(
        messageId = message.messageId,
        extras = ConvertUtils.newString(message.payload),
    )
}