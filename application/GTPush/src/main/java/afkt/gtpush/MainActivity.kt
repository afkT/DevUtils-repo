package afkt.gtpush

import afkt.gtpush.base.BaseActivity
import afkt.gtpush.base.BaseViewModel
import afkt.gtpush.databinding.ActivityMainBinding
import com.therouter.router.Route

@Route(path = AppRouter.PATH_MAIN_ACTIVITY)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main, BR.viewModel
)

class MainViewModel : BaseViewModel() {

    val clickSecondary: () -> Unit = {
        AppRouter.routerSecondaryActivity()
    }
}