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
        AppUMShare.engine()?.openMinApp(
            _activity(view), null, shareListener
        )
    }

    val clickShareMinapp = View.OnClickListener { view ->
        // 分享小程序
        AppUMShare.engine()?.shareMinApp(
            _activity(view), null, shareListener
        )
    }

    val clickShareUrl = View.OnClickListener { view ->
        // 分享链接
        AppUMShare.engine()?.shareUrl(
            _activity(view), null, shareListener
        )
    }

    val clickShareImage = View.OnClickListener { view ->
        // 分享图片
        AppUMShare.engine()?.shareImage(
            _activity(view), null, shareListener
        )
    }

    val clickShareImageList = View.OnClickListener { view ->
        // 分享图片
        AppUMShare.engine()?.shareImageList(
            _activity(view), null, shareListener
        )
    }

    val clickShareText = View.OnClickListener { view ->
        // 分享文本
        AppUMShare.engine()?.shareText(
            _activity(view), null, shareListener
        )
    }

    val clickShareVideo = View.OnClickListener { view ->
        // 分享视频
        AppUMShare.engine()?.shareVideo(
            _activity(view), null, shareListener
        )
    }

    val clickShareMusic = View.OnClickListener { view ->
        // 分享音乐
        AppUMShare.engine()?.shareMusic(
            _activity(view), null, shareListener
        )
    }

    val clickShareEmoji = View.OnClickListener { view ->
        // 分享表情
        AppUMShare.engine()?.shareEmoji(
            _activity(view), null, shareListener
        )
    }

    val clickShareFile = View.OnClickListener { view ->
        // 分享文件
        AppUMShare.engine()?.shareFile(
            _activity(view), null, shareListener
        )
    }

    val clickShare = View.OnClickListener { view ->
        // 分享操作
        AppUMShare.engine()?.share(
            _activity(view), null, shareListener
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