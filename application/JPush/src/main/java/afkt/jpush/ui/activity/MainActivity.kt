package afkt.jpush.ui.activity

import afkt.jpush.R
import afkt.jpush.base.BaseActivity
import afkt.jpush.base.config.RouterPath
import afkt.jpush.databinding.ActivityMainBinding
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = RouterPath.MainActivity_PATH)
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_main

    override fun initListener() {
        super.initListener()

        binding.vidOtherBtn.setOnClickListener {
            routerActivity(RouterPath.OtherActivity_PATH)
        }

        binding.vidDeviceBtn.setOnClickListener {
            routerActivity(RouterPath.DeviceActivity_PATH)
        }
    }
}