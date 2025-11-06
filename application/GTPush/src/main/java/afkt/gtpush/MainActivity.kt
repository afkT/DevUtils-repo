package afkt.gtpush

import afkt.gtpush.base.BaseActivity
import afkt.gtpush.base.BaseViewModel
import afkt.gtpush.databinding.ActivityMainBinding
import com.therouter.router.Route
import dev.base.simple.extensions.asActivity

@Route(path = AppRouter.PATH_MAIN_ACTIVITY)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main, BR.viewModel, simple_Agile = { act ->
        act.asActivity<MainActivity> {
        }
    }
)

class MainViewModel : BaseViewModel() {
}