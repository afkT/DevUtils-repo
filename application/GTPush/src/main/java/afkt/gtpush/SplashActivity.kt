package afkt.gtpush

import afkt.gtpush.base.BaseActivity
import androidx.viewbinding.ViewBinding
import dev.utils.app.HandlerUtils

class SplashActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_splash

    override fun initOther() {
        super.initOther()
        HandlerUtils.postRunnable({
            if (isFinishing) return@postRunnable
            routerActivity(AppRouter.PATH_MAIN_ACTIVITY)
            finish()
        }, 1200)
    }
}