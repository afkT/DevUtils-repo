package afkt.db

import afkt.db.base.BaseActivity
import afkt.db.base.BaseViewModel
import afkt.db.databinding.ActivitySplashBinding
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