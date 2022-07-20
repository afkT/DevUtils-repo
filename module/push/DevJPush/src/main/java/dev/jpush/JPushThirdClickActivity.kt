package dev.jpush

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import dev.engine.DevEngine
import dev.utils.app.JSONObjectUtils
import org.json.JSONObject

// 消息 ID
private const val KEY_MSGID = "msg_id"

// 该通知的下发通道
private const val KEY_WHICH_PUSH_SDK = "rom_type"

// 通知标题
private const val KEY_TITLE = "n_title"

// 通知内容
private const val KEY_CONTENT = "n_content"

// 通知附加字段
private const val KEY_EXTRAS = "n_extras"

/**
 * detail: 极光推送第三方厂商点击回调
 * @author Ttt
 */
class JPushThirdClickActivity : AppCompatActivity() {

    // 日志 TAG
    private val TAG = JPushThirdClickActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 点击通知栏推送消息处理
        intent?.let { handlerPushOp(it) }
        // 关闭当前页面
        finish()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 点击通知栏推送消息处理
        intent?.let { handlerPushOp(it) }
        // 关闭当前页面
        finish()
    }

    // =============
    // = 内部处理方法 =
    // =============

    /**
     * 处理推送操作
     * @param intent 获取传递的数据
     */
    private fun handlerPushOp(intent: Intent) {
        var data: String? = null
        // 获取华为平台附带的 JPUSH 信息
        if (intent.data != null) {
            data = intent.data.toString()
        }
        // 获取 FCM/OPPO/小米/VIVO/魅族 平台附带的 JPUSH 信息
        if (TextUtils.isEmpty(data) && intent.extras != null) {
            data = intent.extras?.getString("JMessageExtra")
        }

        if (TextUtils.isEmpty(data)) return

        DevEngine.getLog()?.dTag(
            TAG, "[handlerPushOp] $data"
        )

        JSONObjectUtils.getJSONObject(data)?.let {
            // 推送消息点击通知
            DevEngine.getPush()?.run {
                onNotificationMessageClicked(
                    this@JPushThirdClickActivity, convertMessage(it)
                )
            }
        }
    }
}

/**
 * detail: 极光第三方厂商消息封装类
 * @author Ttt
 */
class JPushThirdMessage(
    val msgId: String?,
    val title: String?,
    val content: String?,
    val extras: String?,
    val whichPushSDK: Byte,
    val whichPushSDKName: String
)

// =============
// = 对外公开方法 =
// =============

/**
 * 获取推送所属第三方厂商名
 * @param whichPushSDK Byte
 * @return 第三方厂商名
 */
fun getPushSDKName(whichPushSDK: Byte): String {
    return when (whichPushSDK.toInt()) {
        1 -> "XiaoMI"
        2 -> "HuaWei"
        3 -> "MeiZu"
        4 -> "OPPO"
        5 -> "VIVO"
        6 -> "ASUS" // 华硕
        8 -> "FCM" // Firebase
        else -> "jpush"
    }
}

/**
 * JSONObject 转换 JPushThirdMessage 对象
 * @param jsonObject JSONObject
 * @return JPushThirdMessage
 */
fun toJPushThirdMessage(jsonObject: JSONObject): JPushThirdMessage {
    val msgId = jsonObject.optString(KEY_MSGID)
    val title = jsonObject.optString(KEY_TITLE)
    val content = jsonObject.optString(KEY_CONTENT)
    val extras = jsonObject.optString(KEY_EXTRAS)
    val whichPushSDK = jsonObject.optInt(KEY_WHICH_PUSH_SDK).toByte()
    val whichPushSDKName = getPushSDKName(whichPushSDK)
    return JPushThirdMessage(
        msgId, title, content, extras, whichPushSDK, whichPushSDKName
    )
}