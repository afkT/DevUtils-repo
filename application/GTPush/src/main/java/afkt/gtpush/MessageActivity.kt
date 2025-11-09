package afkt.gtpush

import afkt.gtpush.base.BaseActivity
import afkt.gtpush.base.BaseViewModel
import afkt.gtpush.databinding.ActivityMainBinding
import com.therouter.router.Route
import dev.base.simple.extensions.asActivity

/**
 * detail: 推送消息 Activity
 * @author Ttt
 * 点击推送后，根据推送消息跳转对应的路由页
 */
@Route(path = AppRouter.PATH_MESSAGE_ACTIVITY)
class MessageActivity : BaseActivity<ActivityMainBinding, MessageViewModel>(
    R.layout.activity_main, BR.viewModel, simple_Agile = { act ->
        act.asActivity<MessageActivity> {
        }
    }
)

class MessageViewModel : BaseViewModel() {
}