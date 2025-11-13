package afkt.jpush

import afkt.jpush.base.BaseActivity
import afkt.jpush.base.BaseViewModel
import afkt.jpush.databinding.ActivitySplashBinding
import dev.base.simple.extensions.asActivity
import dev.utils.app.assist.DelayAssist

class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>(
    R.layout.activity_splash, BR.viewModel, simple_Agile = { act ->
        act.asActivity<SplashActivity> {
            delayRouter.post()
        }
    }
) {
    val delayRouter = DelayAssist(1200L) {
        AppRouter.routerMainActivity()
        finish()
    }
}