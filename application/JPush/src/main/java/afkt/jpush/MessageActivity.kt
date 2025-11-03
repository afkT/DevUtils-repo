package afkt.jpush

import afkt.jpush.base.BaseActivity
import afkt.jpush.databinding.ActivityMessageBinding
import com.therouter.router.Autowired
import com.therouter.router.Route
import dev.expand.engine.json.toJsonIndent
import dev.utils.DevFinal

/**
 * detail: 推送消息 Activity
 * @author Ttt
 * 点击推送后根据, 推送消息跳转对应的路由页
 */
@Route(path = RouterPath.MessageActivity_PATH)
class MessageActivity : BaseActivity<ActivityMessageBinding>() {

    override fun isToolBar(): Boolean = true

    override fun baseLayoutId(): Int = R.layout.activity_message

    @JvmField
    @Autowired(name = DevFinal.STR.DATA)
    var pushData: String? = null

    override fun initValue() {
        super.initValue()

        binding.vidMessageTv.text = pushData?.toJsonIndent() ?: "error"
    }
}