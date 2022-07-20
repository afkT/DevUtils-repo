package afkt.accessibility.ui.activity

import afkt.accessibility.R
import afkt.accessibility.databinding.ActivityMainBinding
import afkt.accessibility.service.AccessibilityListenerService
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.utils.app.ActivityUtils
import dev.utils.app.assist.floating.FloatingWindowManagerAssist
import dev.utils.app.toast.ToastUtils

/**
 * 具体代码 copy GitHub ActivityTracker 库
 * @see https://github.com/fashare2015/ActivityTracker
 * <p></p>
 * 内部只是封装 AccessibilityListenerService 以及 BaseApplication 结合 DevApp#AccessibilityUtils 使用
 */
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkOverlayPermission()
        // Activity 栈
        binding.vidActivityTrackerBtn.setOnClickListener {
            if (AccessibilityListenerService.checkAccessibility()) {
                startService(
                    Intent(this@MainActivity, AccessibilityListenerService::class.java)
                        .putExtra(
                            AccessibilityListenerService.COMMAND,
                            AccessibilityListenerService.COMMAND_OPEN
                        )
                )
                ActivityUtils.startHomeActivity()
            } else {
                ToastUtils.showShort(R.string.str_open_accessibility_tips)
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (FloatingWindowManagerAssist.isOverlayRequestCode(requestCode)) {
            checkOverlayPermission()
        }
    }

    private fun checkOverlayPermission() {
        if (!FloatingWindowManagerAssist.checkOverlayPermission(this, true)) {
            ToastUtils.showShort(R.string.str_open_floating_tips)
        }
    }
}