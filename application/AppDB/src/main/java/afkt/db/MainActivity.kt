package afkt.db

import afkt.db.base.BaseActivity
import afkt.db.base.BaseViewModel
import afkt.db.databinding.ActivityMainBinding
import android.view.View
import com.therouter.router.Route

@Route(path = AppRouter.PATH_MAIN_ACTIVITY)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main, BR.viewModel
)

class MainViewModel : BaseViewModel() {

    val clickRoom = View.OnClickListener { view ->
        AppRouter.routerRoomActivity()
    }

    val clickGreenDao = View.OnClickListener { view ->
        AppRouter.routerGreenDaoActivity()
    }
}