package afkt.gtpush

import afkt.gtpush.base.BaseActivity
import afkt.gtpush.base.BaseViewModel
import afkt.gtpush.databinding.ActivityMessageBinding
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.therouter.router.Route
import dev.base.simple.extensions.asActivity
import dev.engine.extensions.json.toJsonIndent
import dev.utils.DevFinal

/**
 * detail: 推送消息 Activity
 * @author Ttt
 * 点击推送后，根据推送消息跳转对应的路由页
 */
@Route(path = AppRouter.PATH_MESSAGE_ACTIVITY)
class MessageActivity : BaseActivity<ActivityMessageBinding, MessageViewModel>(
    R.layout.activity_message, BR.viewModel, simple_Agile = { act ->
        act.asActivity<MessageActivity> {
            binding.vidTitle.leftView.setOnClickListener { finish() }
            // 初始化数据
            viewModel.initializeData(intent)
        }
    }
)

class MessageViewModel : BaseViewModel() {

    // 消息内容
    private val _messageText = MutableLiveData<String>()
    val messageText: LiveData<String> get() = _messageText

    /**
     * 初始化数据
     */
    fun initializeData(intent: Intent?) {
        val content = intent?.getStringExtra(
            DevFinal.STR.DATA
        )?.toJsonIndent() ?: "error"
        _messageText.value = content
    }
}