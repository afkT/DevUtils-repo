package afkt.jpush

import afkt.jpush.base.BaseActivity
import afkt.jpush.base.BaseViewModel
import afkt.jpush.databinding.ActivitySecondaryBinding
import com.therouter.router.Route
import dev.base.simple.extensions.asActivity

/**
 * detail: 二级页面
 * @author Ttt
 * 用于演示以 [SplashActivity] -> [MainActivity] -> [SecondaryActivity] 进入顺序后
 * 停留在 [SecondaryActivity] 页面，再点击推送通知栏区分 APP 在后台、前台、未启动各种情况展示效果
 */
@Route(path = AppRouter.PATH_SECONDARY_ACTIVITY)
class SecondaryActivity : BaseActivity<ActivitySecondaryBinding, BaseViewModel>(
    R.layout.activity_secondary, BR.viewModel, simple_Agile = { act ->
        act.asActivity<SecondaryActivity> {
            binding.vidTitle.leftView.setOnClickListener { finish() }
        }
    }
)