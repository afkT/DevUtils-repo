package dev.module.share

import android.graphics.Bitmap
import dev.base.DevSource
import dev.engine.share.IShareEngine

/**
 * detail: 分享 ( 资源 ) 平台
 * @author Ttt
 * 参考友盟 SHARE_MEDIA 自行获取常用分享平台
 * 不直接使用 SHARE_MEDIA 方便使用 Engine 替换操作
 */
enum class SharePlatform {

    // 新浪微博
    SINA,

    // QQ、QQ 空间
    QQ,
    QZONE,

    // 微信、微信朋友圈、微信收藏
    WEIXIN,
    WEIXIN_CIRCLE,
    WEIXIN_FAVORITE,

    // 企业微信
    WXWORK,

    // 支付宝
    ALIPAY,

    // 钉钉
    DINGTALK,

    // 无任何平台分享
    NONE
}

/**
 * detail: 分享类型
 * @author Ttt
 */
enum class ShareType {

    // 打开小程序
    OPEN_MIN_APP,

    // 分享小程序
    SHARE_MIN_APP,

    // 分享链接
    SHARE_URL,

    // 分享图片
    SHARE_IMAGE,

    // 分享多张图片
    SHARE_IMAGE_LIST,

    // 分享文本
    SHARE_TEXT,

    // 分享视频
    SHARE_VIDEO,

    // 分享音乐
    SHARE_MUSIC,

    // 分享表情
    SHARE_EMOJI,

    // 分享文件
    SHARE_FILE,
}

/**
 * detail: 图片压缩类型
 * @author Ttt
 */
enum class ImageCompressStyle {

    // 大小压缩 ( 适合普通很大的图 )
    SCALE,

    // 质量压缩 ( 适合长图分享 )
    QUALITY
}

/**
 * detail: 小程序类型
 * @author Ttt
 */
enum class MiniProgramType {

    // 正式版
    TYPE_RELEASE,

    // 开发版
    TYPE_TEST,

    // 体验版
    TYPE_PREVIEW,
}

// ========
// = 配置类 =
// ========

/**
 * detail: 分享平台 Key
 * @author Ttt
 */
class SharePlatformKey(
    // 分享平台
    val platform: SharePlatform?,
    // APPId
    val appId: String?,
    // APPKey
    val appKey: String? = "",
    // 授权回调页
    val redirectUrl: String? = "",
    // Android FileProvider
    val fileProvider: String? = "",
    // 微信企业号 授权应用 ID
    val agentId: String? = "",
    // 企业微信 Url schema
    val schema: String? = ""
)

/**
 * detail: 分享配置包装类
 * @author Ttt
 */
class ShareConfig(
    // 分享 SDK APPKey
    val appKey: String?,
    // 渠道信息
    val channel: String?,
    val pushSecret: String? = "",
    // 是否 debug 模式
    val isDebugMode: Boolean = false,
    // 分享平台 Key 信息
    val platformKey: List<SharePlatformKey>
) : IShareEngine.EngineConfig()

/**
 * detail: 分享参数包装类
 * @author Ttt
 * 自行根据所需参数进行封装
 */
class ShareParams(
    // 分享平台
    var platform: SharePlatform?,
    // 分享类型
    var shareType: ShareType,
    // 缩略图资源 ( 封面 )
    var thumbnail: DevSource? = null,
    // 分享图片
    var image: DevSource? = null,
    // 分享多图
    val imageList: MutableList<DevSource> = mutableListOf(),
    // 消息标题
    var title: String? = null,
    // 消息描述
    var description: String? = null,
    // 页面路径 ( 小程序页面路径 )
    var path: String? = null,
    // 小程序原始 ID
    var userName: String? = null,
    // 小程序 APPId ( APPKey )
    var miniAppId: String? = null,
    // 分享链接
    var url: String? = null,
    // 跳转链接
    var targetUrl: String? = null,
    // 小程序类型
    var miniProgramType: MiniProgramType = MiniProgramType.TYPE_RELEASE,

    // ==========
    // = 图片处理 =
    // ==========

    // 压缩类型
    var compressStyle: ImageCompressStyle? = null,
    // 压缩存储类型
    var compressFormat: Bitmap.CompressFormat? = null,
) : IShareEngine.EngineItem()