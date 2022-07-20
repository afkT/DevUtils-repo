package afkt.accessibility.utils

import afkt.accessibility.R
import afkt.accessibility.base.model.ActivityChangedEvent
import afkt.accessibility.service.AccessibilityListenerService
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import dev.DevUtils
import dev.utils.app.assist.floating.DevFloatingTouchIMPL
import dev.utils.app.assist.floating.FloatingWindowManagerAssist
import dev.utils.app.assist.floating.IFloatingTouch
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.logger.DevLogger
import dev.utils.app.toast.ToastUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * detail: Activity 跟踪工具类
 * @author Ttt
 */
class TrackerUtils private constructor() {

    companion object {

        val instance: TrackerUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            TrackerUtils()
        }
    }

    // 悬浮窗管理辅助类
    private val mAssist = FloatingWindowManagerAssist()

    // 悬浮窗触摸辅助类实现
    private val mTouchAssist: IFloatingTouch by lazy {
        object : DevFloatingTouchIMPL() {
            override fun updateViewLayout(
                view: View?,
                dx: Int,
                dy: Int
            ) {
                mAssist.updateViewLayout(view, dx, dy)
            }
        }
    }

    // 悬浮 View
    private val mFloatingView: FloatingView by lazy {
        FloatingView(DevUtils.getContext(), mTouchAssist)
    }

    /**
     * 添加悬浮 View
     */
    fun addView() {
        mAssist.addView(mFloatingView)
    }

    /**
     * 移除悬浮 View
     */
    fun removeView() {
        mAssist.removeView(mFloatingView)
    }
}

/**
 * detail: 悬浮 View
 * @author Ttt
 */
@SuppressLint("ViewConstructor")
class FloatingView(
    thisContext: Context,
    private val assist: IFloatingTouch
) : LinearLayout(thisContext) {

    init {
        initialize()
    }

    private var packageNameTv: TextView? = null
    private var classNameTv: TextView? = null

    private fun initialize() {
        View.inflate(context, R.layout.layout_floating, this)
        packageNameTv = findViewById(R.id.vid_package_tv)
        classNameTv = findViewById(R.id.vid_class_tv)
        findViewById<View>(R.id.vid_close_iv).setOnClickListener {
            ToastUtils.showShort(R.string.str_close_floating)

            context.startService(
                Intent(context, AccessibilityListenerService::class.java)
                    .putExtra(
                        AccessibilityListenerService.COMMAND,
                        AccessibilityListenerService.COMMAND_CLOSE
                    )
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return assist.onTouchEvent(this, event)
    }

    // =========
    // = Event =
    // =========

    private val TAG = FloatingView::class.java.simpleName

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        EventBusUtils.register(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        EventBusUtils.unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: ActivityChangedEvent?) {
        event?.let {
            val packageName = it.packageName
            val className = it.className

            val trimName = if (className.startsWith(packageName)) {
                className.substring(packageName.length)
            } else {
                className
            }
            DevLogger.dTag(TAG, "%s -> %s", packageName, trimName)

            ViewHelper.get()
                .setText(packageName, packageNameTv)
                .setText(trimName, classNameTv)
        }
    }
}