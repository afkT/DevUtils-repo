package afkt.umshare

import afkt.umshare.base.BaseActivity
import afkt.umshare.base.BaseViewModel
import afkt.umshare.databinding.ActivityMainBinding
import android.app.Activity
import android.content.Intent
import android.view.View
import com.therouter.router.Route
import dev.engine.DevEngine
import dev.engine.extensions.log.log_dTag
import dev.engine.share.listener.ShareListener
import dev.module.share.ShareParams
import dev.utils.app.ActivityUtils
import dev.utils.common.ThrowableUtils

@Route(path = AppRouter.PATH_MAIN_ACTIVITY)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main, BR.viewModel
) {

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
}

class MainViewModel : BaseViewModel() {

    private fun _activity(view: View): Activity? {
        return ActivityUtils.getActivity(view)
    }

    // ==========
    // = 点击事件 =
    // ==========

    val clickOpenMinapp = View.OnClickListener { view ->
        // 打开小程序
        val params: ShareParams? = null
        AppUMShare.engine()?.openMinApp(
            _activity(view), params, shareListener
        )
    }

    val clickShareMinapp = View.OnClickListener { view ->
        // 分享小程序
        val params: ShareParams? = null
        AppUMShare.engine()?.shareMinApp(
            _activity(view), params, shareListener
        )
    }

    val clickShareUrl = View.OnClickListener { view ->
        // 分享链接
        val params: ShareParams? = null
        AppUMShare.engine()?.shareUrl(
            _activity(view), params, shareListener
        )
    }

    val clickShareImage = View.OnClickListener { view ->
        // 分享图片
        val params: ShareParams? = null
        AppUMShare.engine()?.shareImage(
            _activity(view), params, shareListener
        )
    }

    val clickShareImageList = View.OnClickListener { view ->
        // 分享图片
        val params: ShareParams? = null
        AppUMShare.engine()?.shareImageList(
            _activity(view), params, shareListener
        )
    }

    val clickShareText = View.OnClickListener { view ->
        // 分享文本
        val params: ShareParams? = null
        AppUMShare.engine()?.shareText(
            _activity(view), params, shareListener
        )
    }

    val clickShareVideo = View.OnClickListener { view ->
        // 分享视频
        val params: ShareParams? = null
        AppUMShare.engine()?.shareVideo(
            _activity(view), params, shareListener
        )
    }

    val clickShareMusic = View.OnClickListener { view ->
        // 分享音乐
        val params: ShareParams? = null
        AppUMShare.engine()?.shareMusic(
            _activity(view), params, shareListener
        )
    }

    val clickShareEmoji = View.OnClickListener { view ->
        // 分享表情
        val params: ShareParams? = null
        AppUMShare.engine()?.shareEmoji(
            _activity(view), params, shareListener
        )
    }

    val clickShareFile = View.OnClickListener { view ->
        // 分享文件
        val params: ShareParams? = null
        AppUMShare.engine()?.shareFile(
            _activity(view), params, shareListener
        )
    }

    val clickShare = View.OnClickListener { view ->
        // 分享操作
        val params: ShareParams? = null
        AppUMShare.engine()?.share(
            _activity(view), params, shareListener
        )
    }

    // ==========
    // = 分享事件 =
    // ==========

    // 分享回调事件
    private val shareListener = object : ShareListener<ShareParams> {
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