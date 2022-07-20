package afkt.umshare.ui.activity

import afkt.umshare.R
import afkt.umshare.base.BaseActivity
import afkt.umshare.base.config.RouterPath
import afkt.umshare.databinding.ActivityMainBinding
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.DevEngine
import dev.engine.share.listener.ShareListener
import dev.kotlin.engine.log.log_dTag
import dev.module.share.ShareParams
import dev.utils.common.ThrowableUtils

@Route(path = RouterPath.MainActivity_PATH)
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_main

    override fun initListener() {
        super.initListener()

        binding.vidOpenMinappBtn.setOnClickListener {
            // 打开小程序
            DevEngine.getShare()?.openMinApp(
                this, null, shareListener
            )
        }

        binding.vidShareMinappBtn.setOnClickListener {
            // 分享小程序
            DevEngine.getShare()?.shareMinApp(
                this, null, shareListener
            )
        }

        binding.vidShareUrlBtn.setOnClickListener {
            // 分享链接
            DevEngine.getShare()?.shareUrl(
                this, null, shareListener
            )
        }

        binding.vidShareImageBtn.setOnClickListener {
            // 分享图片
            DevEngine.getShare()?.shareImage(
                this, null, shareListener
            )
        }

        binding.vidShareImageListBtn.setOnClickListener {
            // 分享图片
            DevEngine.getShare()?.shareImageList(
                this, null, shareListener
            )
        }

        binding.vidShareTextBtn.setOnClickListener {
            // 分享文本
            DevEngine.getShare()?.shareText(
                this, null, shareListener
            )
        }

        binding.vidShareVideoBtn.setOnClickListener {
            // 分享视频
            DevEngine.getShare()?.shareVideo(
                this, null, shareListener
            )
        }

        binding.vidShareMusicBtn.setOnClickListener {
            // 分享音乐
            DevEngine.getShare()?.shareMusic(
                this, null, shareListener
            )
        }

        binding.vidShareEmojiBtn.setOnClickListener {
            // 分享表情
            DevEngine.getShare()?.shareEmoji(
                this, null, shareListener
            )
        }

        binding.vidShareFileBtn.setOnClickListener {
            // 分享文件
            DevEngine.getShare()?.shareFile(
                this, null, shareListener
            )
        }

        binding.vidShareBtn.setOnClickListener {
            // 分享操作
            DevEngine.getShare()?.share(
                this, null, shareListener
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, intent)

        DevEngine.getShare()?.onActivityResult(
            this, requestCode, resultCode, intent
        )
    }

    // ==========
    // = 分享事件 =
    // ==========

    // 分享回调事件
    private val shareListener: ShareListener<ShareParams> by lazy {
        object : ShareListener<ShareParams> {
            override fun onStart(params: ShareParams?) {
                TAG.log_dTag(
                    message = "开始分享"
                )
            }

            override fun onResult(params: ShareParams?) {
                TAG.log_dTag(
                    message = "分享成功"
                )
            }

            override fun onError(
                params: ShareParams?,
                throwable: Throwable?
            ) {
                TAG.log_dTag(
                    message = "分享失败\n${ThrowableUtils.getThrowable(throwable)}"
                )
            }

            override fun onCancel(params: ShareParams?) {
                TAG.log_dTag(
                    message = "取消分享"
                )
            }
        }
    }
}