package dev.module.push

import android.os.Bundle
import android.os.Parcelable
import dev.engine.push.IPushEngine
import kotlinx.parcelize.Parcelize

/**
 * detail: 推送消息包装类
 * @author Ttt
 * 自行根据所需参数进行封装
 */
@Parcelize
class PushMessage(
    val messageId: String? = "",
    val title: String? = "",
    val content: String? = "",
    val extras: String? = "",
    val whichPushSDK: Int = 0,
    val whichPushSDKName: String? = "",
    val code: String? = "",

    // ==========
    // = 额外扩展 =
    // ==========

    val cmd: String? = "",
    val extrasBundle: Bundle? = null,
    val errorCode: String? = "",
    val msg: String? = "",
) : Parcelable,
    IPushEngine.EngineItem()

/**
 * detail: 推送配置包装类
 * @author Ttt
 */
class PushConfig(
    val isDebugMode: Boolean = false
) : IPushEngine.EngineConfig()