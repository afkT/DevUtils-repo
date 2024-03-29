package afkt.gtpush.device

import afkt.gtpush.R
import afkt.gtpush.base.BaseActivity
import afkt.gtpush.base.config.RouterPath
import afkt.gtpush.databinding.ActivityDeviceBinding
import android.content.DialogInterface
import com.therouter.router.Route
import dev.utils.app.DevicePolicyUtils
import dev.utils.app.DialogUtils
import dev.utils.app.toast.ToastTintUtils

@Route(path = RouterPath.DeviceActivity_PATH)
class DeviceActivity : BaseActivity<ActivityDeviceBinding>() {

    override fun isToolBar(): Boolean = true

    override fun baseLayoutId(): Int = R.layout.activity_device

    override fun initListener() {
        super.initListener()

        DevicePolicyUtils.getInstance()
            .setComponentName(DeviceReceiver::class.java)

        // 激活组件 ( 申请权限 )
        binding.vidActiveBtn.setOnClickListener {
            if (DevicePolicyUtils.getInstance().isAdminActive) {
                showToast(true, "已激活组件")
                return@setOnClickListener
            }
            showToast(
                DevicePolicyUtils.getInstance().activeAdmin("需开启权限允许对设备进行操作!")
            )
        }

        // 移除组件
        binding.vidRemoveActiveBtn.setOnClickListener {
            DialogUtils.createAlertDialog(this, "移除组件",
                "是否移除组件 ( 移除设备管理权限 )", "取消", "确认",
                object : DialogUtils.DialogListener() {
                    override fun onRightButton(dialog: DialogInterface?) {
                        showToast(
                            DevicePolicyUtils.getInstance().removeActiveAdmin()
                        )
                    }
                }).show()
        }

        // 锁屏
        binding.vidLockBtn.setOnClickListener {
            showToast(
                DevicePolicyUtils.getInstance().lockNow()
            )
        }

        // 延迟锁屏
        binding.vidDelayLockBtn.setOnClickListener {
            showToast(
                DevicePolicyUtils.getInstance().lockByTime(30000L)
            )
        }
    }

    private fun showToast(result: Boolean) {
        showToast(result, if (result) "操作成功" else "操作失败")
    }

    private fun showToast(
        result: Boolean,
        text: String
    ) {
        if (result) {
            ToastTintUtils.success(text)
        } else {
            ToastTintUtils.error(text)
        }
    }
}