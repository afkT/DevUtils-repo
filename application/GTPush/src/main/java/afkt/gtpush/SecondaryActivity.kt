package afkt.gtpush

import afkt.gtpush.base.BaseActivity
import afkt.gtpush.base.BaseViewModel
import afkt.gtpush.databinding.ActivityMainBinding
import com.therouter.router.Route
import dev.base.simple.extensions.asActivity

/**
 * detail: 二级页面
 * @author Ttt
 * 用于演示以 [SplashActivity] -> [MainActivity] -> [SecondaryActivity] 进入顺序后
 * 点击推送通知栏区分 APP 在后台、前台、未启动各种情况展示效果
 */
@Route(path = AppRouter.PATH_SECONDARY_ACTIVITY)
class SecondaryActivity : BaseActivity<ActivityMainBinding, SecondaryViewModel>(
    R.layout.activity_main, BR.viewModel, simple_Agile = { act ->
        act.asActivity<SecondaryActivity> {
        }
    }
)

class SecondaryViewModel : BaseViewModel() {
}