package afkt.jpush

import afkt.jpush.base.BaseActivity
import afkt.jpush.base.BaseViewModel
import afkt.jpush.databinding.ActivityMainBinding
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