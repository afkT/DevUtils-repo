package afkt.gtpush

import afkt.gtpush.base.BaseActivity
import afkt.gtpush.databinding.ActivityMainBinding
import com.therouter.router.Route

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