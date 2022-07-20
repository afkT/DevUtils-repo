package dev.umshare

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.media.*
import dev.engine.share.IShareEngine
import dev.engine.share.listener.ShareListener
import dev.module.share.*

/**
 * detail: 友盟分享 Engine 实现
 * @author Ttt
 */
class UMShareEngine : IShareEngine<ShareConfig, ShareParams> {

    override fun initialize(
        application: Application?,
        config: ShareConfig?
    ) {
        application?.let {
            config?.let { sc ->
                UMConfigure.init(
                    it, sc.appKey, sc.channel,
                    UMConfigure.DEVICE_TYPE_PHONE,
                    sc.pushSecret
                )
                // 友盟分享 Log 控制开关
                UMConfigure.setLogEnabled(sc.isDebugMode)
                // 配置分享平台 Key 信息
                sc.platformKey.forEach {
                    when (it.platform) {
                        SharePlatform.SINA -> {
                            // 新浪微博设置
                            PlatformConfig.setSinaWeibo(
                                it.appId, it.appKey,
                                it.redirectUrl
                            )
                            // 新浪微博必须统一设置为
                            // PlatformConfig.setSinaFileProvider(应用包名.fileprovider)
                            PlatformConfig.setSinaFileProvider(
                                it.fileProvider
                            )
                        }
                        SharePlatform.QQ,
                        SharePlatform.QZONE -> {
                            // QQ 设置
                            PlatformConfig.setQQZone(
                                it.appId, it.appKey
                            )
                            PlatformConfig.setQQFileProvider(
                                it.fileProvider
                            )
                        }
                        SharePlatform.WEIXIN,
                        SharePlatform.WEIXIN_CIRCLE,
                        SharePlatform.WEIXIN_FAVORITE -> {
                            // 微信设置
                            PlatformConfig.setWeixin(
                                it.appId, it.appKey
                            )
                            PlatformConfig.setWXFileProvider(
                                it.fileProvider
                            )
                        }
                        SharePlatform.WXWORK -> {
                            // 企业微信设置
                            PlatformConfig.setWXWork(
                                it.appId, it.appKey,
                                it.agentId, it.schema
                            )
                            PlatformConfig.setWXWorkFileProvider(
                                it.fileProvider
                            )
                        }
                        SharePlatform.ALIPAY -> {
                            // 支付宝设置
                            PlatformConfig.setAlipay(it.appId)
                        }
                        SharePlatform.DINGTALK -> {
                            // 钉钉设置
                            PlatformConfig.setDing(it.appId)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    // ==========
    // = 分享操作 =
    // ==========

    /**
     * 打开小程序
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun openMinApp(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            try {
                if (isWEIXIN(params.platform)) {
                    val api = WXAPIFactory.createWXAPI(activity, params.miniAppId)
                    val req = WXLaunchMiniProgram.Req()
                    // 小程序原始 ID
                    req.userName = params.userName
                    // 小程序页面路径
                    req.path = params.path
                    // 小程序类型
                    req.miniprogramType = convertMiniProgramType(params.miniProgramType)
                    // 打开小程序
                    api.sendReq(req)
                    return true
                }
                // 平台不支持事件触发回调
                listenerTriggerByNotSupportPlatform(
                    params.platform, params.shareType, shareListener
                )
            } catch (error: Exception) {
                // 分享异常捕获事件触发回调
                listenerTriggerByError(params, error, shareListener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享小程序
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun shareMinApp(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            try {
                // 小程序类型支持分享微信小程序和 QQ 小程序
                // 其中微信小程序只支持分享到微信好友、朋友圈, 微信收藏暂不支持
                if (isWEIXIN(params.platform) || isWEIXIN_CIRCLE(params.platform)) {
                    // 兼容低版本的网页链接
                    val miniProgram = UMMin(params.url)
                    // 小程序消息封面图片
                    miniProgram.setThumb(convertShareImage(activity, params, params.thumbnail!!))
                    // 小程序消息 title
                    miniProgram.title = params.title
                    // 小程序消息描述
                    miniProgram.description = params.description
                    // 小程序页面路径
                    miniProgram.path = params.path
                    // 小程序原始 ID ( 在微信平台查询 )
                    miniProgram.userName = params.userName
                    // 分享操作
                    ShareAction(activity)
                        .withMedia(miniProgram)
                        .setPlatform(convertSharePlatform(params.platform))
                        .setCallback(shareListener)
                        .share()
                    return true
                } else if (isQQ(params.platform)) {
                    // 兼容低版本的网页链接
                    val miniProgram = UMQQMini(params.url)
                    // 小程序消息封面图片 ( 缩略图支持网络图片和本地图片 )
                    miniProgram.setThumb(convertShareImage(activity, params, params.thumbnail!!))
                    // 小程序消息 title
                    miniProgram.title = params.title
                    // 小程序消息描述
                    miniProgram.description = params.description
                    // 小程序页面路径
                    miniProgram.path = params.path
                    // 小程序原始 ID
                    miniProgram.miniAppId = params.userName
                    // 分享操作
                    ShareAction(activity)
                        .withMedia(miniProgram)
                        .setPlatform(convertSharePlatform(params.platform))
                        .setCallback(shareListener)
                        .share()
                    return true
                }
                // 平台不支持事件触发回调
                listenerTriggerByNotSupportPlatform(
                    params.platform, params.shareType, shareListener
                )
            } catch (error: Exception) {
                // 分享异常捕获事件触发回调
                listenerTriggerByError(params, error, shareListener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享链接
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun shareUrl(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            try {
                // 链接地址
                val web = UMWeb(params.url)
                // 链接缩略图 ( 缩略图支持网络图片和本地图片 )
                web.setThumb(convertShareImage(activity, params, params.thumbnail!!))
                // 链接标题
                web.title = params.title
                // 链接描述
                web.description = params.description
                // 分享操作
                ShareAction(activity)
                    .withMedia(web)
                    .setPlatform(convertSharePlatform(params.platform))
                    .setCallback(shareListener)
                    .share()
                return true
            } catch (error: Exception) {
                // 分享异常捕获事件触发回调
                listenerTriggerByError(params, error, shareListener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享图片
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun shareImage(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            try {
                // 分享图片
                val image = convertShareImage(activity, params, params.image!!)
                // 判断是否需要缩略图
                if (params.thumbnail != null) {
                    image.setThumb(convertShareImage(activity, params, params.thumbnail!!))
                }
                // 分享操作
                ShareAction(activity)
                    .withMedia(image)
                    .withText(params.description)
                    .setPlatform(convertSharePlatform(params.platform))
                    .setCallback(shareListener)
                    .share()
                return true
            } catch (error: Exception) {
                // 分享异常捕获事件触发回调
                listenerTriggerByError(params, error, shareListener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享多张图片
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun shareImageList(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            try {
                // 现在支持多图分享的平台有两个, 一个是新浪微博一个是 QQ 空间
                // 注意上传多图需要带文字描述 ( withText )

                if (isQZONE(params.platform) || isSINA(params.platform)) {
                    val images = mutableListOf<UMImage>()
                    params.imageList.forEach {
                        images.add(convertShareImage(activity, params, it))
                    }
                    // 分享操作
                    ShareAction(activity)
                        .withMedias(*images.toTypedArray())
                        .withText(params.description)
                        .setPlatform(convertSharePlatform(params.platform))
                        .setCallback(shareListener)
                        .share()
                    return true
                }
                // 平台不支持事件触发回调
                listenerTriggerByNotSupportPlatform(
                    params.platform, params.shareType, shareListener
                )
            } catch (error: Exception) {
                // 分享异常捕获事件触发回调
                listenerTriggerByError(params, error, shareListener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享文本
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun shareText(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            try {
                // QQ 不支持纯文本方式的分享, 但 QQ 空间支持
                if (isQZONE(params.platform)) {
                    // 分享操作
                    ShareAction(activity)
                        .withText(params.description)
                        .setPlatform(convertSharePlatform(params.platform))
                        .setCallback(shareListener)
                        .share()
                    return true
                }
                // 平台不支持事件触发回调
                listenerTriggerByNotSupportPlatform(
                    params.platform, params.shareType, shareListener
                )
            } catch (error: Exception) {
                // 分享异常捕获事件触发回调
                listenerTriggerByError(params, error, shareListener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享视频
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun shareVideo(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            try {
                // 视频只能使用网络视频

                // 视频链接
                val video = UMVideo(params.url)
                // 视频缩略图
                video.setThumb(convertShareImage(activity, params, params.thumbnail!!))
                // 视频标题
                video.title = params.title
                // 视频描述
                video.description = params.description
                // 分享操作
                ShareAction(activity)
                    .withMedia(video)
                    .withText(params.description)
                    .setPlatform(convertSharePlatform(params.platform))
                    .setCallback(shareListener)
                    .share()
                return true
            } catch (error: Exception) {
                // 分享异常捕获事件触发回调
                listenerTriggerByError(params, error, shareListener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享音乐
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun shareMusic(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            try {
                // 音乐只能使用网络音乐

                // 音乐链接
                val music = UMusic(params.url)
                // 音乐缩略图
                music.setThumb(convertShareImage(activity, params, params.thumbnail!!))
                // 音乐标题
                music.title = params.title
                // 音乐描述
                music.description = params.description
                // 音乐点击后跳转链接
                music.setmTargetUrl(params.targetUrl)
                // 分享操作
                ShareAction(activity)
                    .withMedia(music)
                    .setPlatform(convertSharePlatform(params.platform))
                    .setCallback(shareListener)
                    .share()
                return true
            } catch (error: Exception) {
                // 分享异常捕获事件触发回调
                listenerTriggerByError(params, error, shareListener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享表情
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun shareEmoji(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            try {
                // 目前只有微信好友分享支持 Emoji 表情, 其他平台暂不支持
                if (isWEIXIN(params.platform)) {
                    val emoji = convertShareEmoji(activity, params, params.image!!)
                    // 缩略图
                    emoji.setThumb(convertShareImage(activity, params, params.thumbnail!!))
                    // 分享操作
                    ShareAction(activity)
                        .withMedia(emoji)
                        .setPlatform(convertSharePlatform(params.platform))
                        .setCallback(shareListener)
                        .share()
                    return true
                }
                // 平台不支持事件触发回调
                listenerTriggerByNotSupportPlatform(
                    params.platform, params.shareType, shareListener
                )
            } catch (error: Exception) {
                // 分享异常捕获事件触发回调
                listenerTriggerByError(params, error, shareListener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享文件
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun shareFile(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        // 转换分享事件
        val shareListener = convertShareListener(params, listener)
        if (activity != null && params != null) {
            // 平台不支持事件触发回调
            listenerTriggerByNotSupportPlatform(
                params.platform, params.shareType, shareListener
            )
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    /**
     * 分享操作 ( 通用扩展 )
     * @param activity Activity
     * @param params   Share ( Data、Params ) Item
     * @param listener 分享回调
     * @return `true` success, `false` fail
     */
    override fun share(
        activity: Activity?,
        params: ShareParams?,
        listener: ShareListener<ShareParams>?
    ): Boolean {
        if (activity != null && params != null) {
            return when (params.shareType) {
                // 打开小程序
                ShareType.OPEN_MIN_APP -> openMinApp(activity, params, listener)
                // 分享小程序
                ShareType.SHARE_MIN_APP -> shareMinApp(activity, params, listener)
                // 分享链接
                ShareType.SHARE_URL -> shareUrl(activity, params, listener)
                // 分享图片
                ShareType.SHARE_IMAGE -> shareImage(activity, params, listener)
                // 分享多张图片
                ShareType.SHARE_IMAGE_LIST -> shareImageList(activity, params, listener)
                // 分享文本
                ShareType.SHARE_TEXT -> shareText(activity, params, listener)
                // 分享视频
                ShareType.SHARE_VIDEO -> shareVideo(activity, params, listener)
                // 分享音乐
                ShareType.SHARE_MUSIC -> shareMusic(activity, params, listener)
                // 分享表情
                ShareType.SHARE_EMOJI -> shareEmoji(activity, params, listener)
                // 分享文件
                ShareType.SHARE_FILE -> shareFile(activity, params, listener)
            }
        } else {
            // 参数无效事件触发回调
            listenerTriggerByInvalidParams(activity, params, listener)
        }
        return false
    }

    // =

    /**
     * 部分平台 Activity onActivityResult 额外调用处理
     * @param context     Context
     * @param requestCode 请求 code
     * @param resultCode  resultCode
     * @param intent      Intent
     */
    override fun onActivityResult(
        context: Context?,
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ) {
        context?.let {
            UMShareAPI.get(context).onActivityResult(
                requestCode, resultCode, intent
            )
        }
    }
}