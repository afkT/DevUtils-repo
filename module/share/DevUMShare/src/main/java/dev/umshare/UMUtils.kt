package dev.umshare

import android.app.Activity
import android.content.Context
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMEmoji
import com.umeng.socialize.media.UMImage
import dev.base.DevSource
import dev.engine.DevEngine
import dev.engine.share.listener.ShareListener
import dev.module.share.*
import dev.utils.app.ResourceUtils
import dev.utils.app.image.ImageUtils
import dev.utils.common.StreamUtils

/**
 * 转换小程序类型
 * @param miniProgramType MiniProgramType
 */
internal fun convertMiniProgramType(miniProgramType: MiniProgramType): Int {
    return when (miniProgramType) {
        // 正式版
        MiniProgramType.TYPE_RELEASE -> WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE
        // 开发版
        MiniProgramType.TYPE_TEST -> WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST
        // 体验版
        MiniProgramType.TYPE_PREVIEW -> WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW
    }
}

/**
 * 转换图片压缩类型
 * @param compressStyle ImageCompressStyle?
 * @return UMImage.CompressStyle
 */
internal fun convertImageCompressStyle(compressStyle: ImageCompressStyle?): UMImage.CompressStyle? {
    return compressStyle?.let {
        when (it) {
            // 大小压缩 ( 适合普通很大的图 )
            ImageCompressStyle.SCALE -> UMImage.CompressStyle.SCALE
            // 质量压缩 ( 适合长图分享 )
            ImageCompressStyle.QUALITY -> UMImage.CompressStyle.QUALITY
        }
    }
}

/**
 * 通过 [DevSource] 转换 [UMEmoji]
 * @param context Context
 * @param params   Share ( Data、Params ) Item
 * @param source DevSource
 * @return UMEmoji
 * 推荐使用网络图片和资源文件的方式 ( 平台兼容性更高 )
 * 部分平台分享的图片需要设置缩略图
 * 用户设置的图片大小最好不要超过 250k, 缩略图不要超过 18k
 */
internal fun convertShareEmoji(
    context: Context,
    params: ShareParams,
    source: DevSource
): UMEmoji {
    var emoji: UMEmoji? = null
    // 网络图片
    if (source.isUrl) emoji = UMEmoji(context, source.mUrl)
    // 资源文件
    if (source.isResource) emoji = UMEmoji(context, source.mResource)
    // 本地文件
    if (source.isFile) emoji = UMEmoji(context, source.mFile)
    // Uri
    if (source.isUri) {
        val stream = ResourceUtils.openInputStream(source.mUri)
        val bytes = StreamUtils.inputStreamToBytes(stream)
        emoji = UMEmoji(context, bytes)
    }
    // InputStream
    if (source.isInputStream) {
        val bytes = StreamUtils.inputStreamToBytes(source.mInputStream)
        emoji = UMEmoji(context, bytes)
    }
    // 字节流
    if (source.isBytes) emoji = UMEmoji(context, source.mBytes)
    // Bitmap
    if (source.isBitmap) emoji = UMEmoji(context, source.mBitmap)
    // Drawable
    if (source.isDrawable) {
        emoji = UMEmoji(context, ImageUtils.drawableToBitmap(source.mDrawable))
    }
    emoji?.let {
        // 压缩类型
        val compressStyle = convertImageCompressStyle(params.compressStyle)
        if (compressStyle != null) {
            it.compressStyle = compressStyle
        }
        // 压缩存储类型
        if (params.compressFormat != null) {
            it.compressFormat = params.compressFormat
        }
        return it
    }
    throw Exception("暂不支持无效资源")
}

/**
 * 通过 [DevSource] 转换 [UMImage]
 * @param context Context
 * @param params   Share ( Data、Params ) Item
 * @param source DevSource
 * @return UMImage
 * 推荐使用网络图片和资源文件的方式 ( 平台兼容性更高 )
 * 部分平台分享的图片需要设置缩略图
 * 用户设置的图片大小最好不要超过 250k, 缩略图不要超过 18k
 */
internal fun convertShareImage(
    context: Context,
    params: ShareParams,
    source: DevSource
): UMImage {
    var image: UMImage? = null
    // 网络图片
    if (source.isUrl) image = UMImage(context, source.mUrl)
    // 资源文件
    if (source.isResource) image = UMImage(context, source.mResource)
    // 本地文件
    if (source.isFile) image = UMImage(context, source.mFile)
    // Uri
    if (source.isUri) {
        val stream = ResourceUtils.openInputStream(source.mUri)
        val bytes = StreamUtils.inputStreamToBytes(stream)
        image = UMImage(context, bytes)
    }
    // InputStream
    if (source.isInputStream) {
        val bytes = StreamUtils.inputStreamToBytes(source.mInputStream)
        image = UMImage(context, bytes)
    }
    // 字节流
    if (source.isBytes) image = UMImage(context, source.mBytes)
    // Bitmap
    if (source.isBitmap) image = UMImage(context, source.mBitmap)
    // Drawable
    if (source.isDrawable) {
        image = UMImage(context, ImageUtils.drawableToBitmap(source.mDrawable))
    }
    image?.let {
        // 压缩类型
        val compressStyle = convertImageCompressStyle(params.compressStyle)
        if (compressStyle != null) {
            it.compressStyle = compressStyle
        }
        // 压缩存储类型
        if (params.compressFormat != null) {
            it.compressFormat = params.compressFormat
        }
        return it
    }
    throw Exception("暂不支持无效资源")
}

/**
 * 通过 [SharePlatform] 转换 [SHARE_MEDIA]
 * @param platform 分享 ( 资源 ) 平台
 * @param throwError 是否抛出异常
 * @return SHARE_MEDIA
 */
internal fun convertSharePlatform(
    platform: SharePlatform?,
    throwError: Boolean = true
): SHARE_MEDIA {
    return when (platform) {
        SharePlatform.SINA -> SHARE_MEDIA.SINA
        SharePlatform.QQ -> SHARE_MEDIA.QQ
        SharePlatform.QZONE -> SHARE_MEDIA.QZONE
        SharePlatform.WEIXIN -> SHARE_MEDIA.WEIXIN
        SharePlatform.WEIXIN_CIRCLE -> SHARE_MEDIA.WEIXIN_CIRCLE
        SharePlatform.WEIXIN_FAVORITE -> SHARE_MEDIA.WEIXIN_FAVORITE
        SharePlatform.WXWORK -> SHARE_MEDIA.WXWORK
        SharePlatform.ALIPAY -> SHARE_MEDIA.ALIPAY
        SharePlatform.DINGTALK -> SHARE_MEDIA.DINGTALK
        else -> {
            if (throwError) {
                throw Exception("暂不支持平台 $platform")
            } else {
                SHARE_MEDIA.MORE
            }
        }
    }
}

private const val LOG_TAG = "UM_Utils_Listener"

/**
 * 转换分享事件
 * @param params ShareParams
 * @param listener 分享回调
 * @return UMShareListener
 */
internal fun convertShareListener(
    params: ShareParams?,
    listener: ShareListener<ShareParams>?
): UMShareListener {
    val operate = params?.let {
        "params platform ${it.platform}, shareType ${it.shareType}"
    } ?: "params is null"

    return object : UMShareListener {
        override fun onStart(platform: SHARE_MEDIA?) {
            DevEngine.getLog()?.dTag(
                LOG_TAG, "onStart: UM $platform, $operate"
            )
            // 触发回调
            listener?.onStart(params)
        }

        override fun onResult(platform: SHARE_MEDIA?) {
            DevEngine.getLog()?.dTag(
                LOG_TAG, "onResult: UM $platform, $operate"
            )
            // 触发回调
            listener?.onResult(params)
        }

        override fun onError(
            platform: SHARE_MEDIA?,
            error: Throwable?
        ) {
            DevEngine.getLog()?.eTag(
                LOG_TAG, error, "onError: UM $platform, $operate"
            )
            // 触发回调
            listener?.onError(params, error)
        }

        override fun onCancel(platform: SHARE_MEDIA?) {
            DevEngine.getLog()?.dTag(
                LOG_TAG, "onCancel: UM $platform, $operate"
            )
            // 触发回调
            listener?.onCancel(params)
        }
    }
}

/**
 * 平台不支持事件触发回调
 * @param platform 分享 ( 资源 ) 平台
 * @param shareType 分享类型
 * @param listener 分享回调
 */
internal fun listenerTriggerByNotSupportPlatform(
    platform: SharePlatform?,
    shareType: ShareType,
    listener: UMShareListener
) {
    listener.onError(SHARE_MEDIA.MORE, Exception("$platform 平台暂不支持 $shareType 操作"))
}

/**
 * 分享异常捕获事件触发回调
 * @param params ShareParams
 * @param error Exception
 * @param listener 分享回调
 */
internal fun listenerTriggerByError(
    params: ShareParams?,
    error: Exception,
    listener: UMShareListener
) {
    params?.let {
        listener.onError(
            convertSharePlatform(it.platform, false), error
        )
    }
}

/**
 * 参数无效事件触发回调
 * @param activity Activity
 * @param params ShareParams
 * @param listener 分享回调
 */
internal fun listenerTriggerByInvalidParams(
    activity: Activity?,
    params: ShareParams?,
    listener: ShareListener<ShareParams>?
) {
    listener?.let {
        val builder = StringBuilder()
        if (activity == null) {
            builder.append("activity is null")
        }
        if (params == null) {
            if (activity == null) builder.append("、")
            builder.append("params is null")
        }
        // 触发回调
        it.onError(params, Exception(builder.toString()))
    }
}