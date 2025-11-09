package afkt.gtpush

import afkt.gtpush.base.BaseActivity
import afkt.gtpush.base.BaseViewModel
import afkt.gtpush.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>(
    R.layout.activity_splash, BR.viewModel
)