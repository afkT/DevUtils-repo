package afkt.gtpush.ui.activity

import afkt.gtpush.R
import afkt.gtpush.base.BaseActivity
import afkt.gtpush.base.config.RouterPath
import androidx.viewbinding.ViewBinding
import com.therouter.router.Route

/**
 * detail: 其他页面 ( 二级页面 )
 * @author Ttt
 * 用于演示以 [SplashActivity] -> [MainActivity] -> [OtherActivity] 进入顺序后
 * 点击推送通知栏区分 APP 在后台、前台、未启动各种情况展示效果
 */
@Route(path = RouterPath.OtherActivity_PATH)
class OtherActivity : BaseActivity<ViewBinding>() {

    override fun isToolBar(): Boolean = true

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_other
}