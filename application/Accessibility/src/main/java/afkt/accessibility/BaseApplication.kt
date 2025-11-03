package afkt.accessibility

import afkt.accessibility.service.AccessibilityListenerService
import afkt.accessibility.utils.ActivityChangedEvent
import afkt.accessibility.utils.EventBusUtils
import android.text.TextUtils
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.utils.DevFinal
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.common.StringUtils

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : MultiDexApplication() {

    // 日志 TAG
    val TAG = "Accessibility_TAG"

    override fun onCreate() {
        super.onCreate()

        // 打开 lib 内部日志 - 线上环境, 不调用方法
        DevUtils.openLog()
        DevUtils.openDebug()
        // 初始化 Logger 配置
        val logConfig = LogConfig.getSortLogConfig(TAG)
            .displayThreadInfo(false)
        DevLogger.initialize(logConfig)

        // ============
        // = 初始化操作 =
        // ============

        // 初始化服务
        initService()
    }

    /**
     * 初始化服务
     */
    private fun initService() {
        AccessibilityListenerService.setListener(object :
            AccessibilityListenerService.Listener {
            override fun onAccessibilityEvent(
                event: AccessibilityEvent?,
                service: AccessibilityListenerService?
            ) {
//                // 打印 AccessibilityEvent 信息日志
//                AccessibilityUtils.Print.logEvent(event)?.let { log ->
//                    DevLogger.d(log)
//                }
//                // 打印 AccessibilityEvent、AccessibilityService 完整信息日志
//                AccessibilityUtils.Print.logComplete(event, service)?.let { log ->
//                    DevLogger.d(log)
//                }

                event?.let {
                    // 窗口改变时通知
                    if (it.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                        val packageName = it.packageName
                        val className = it.className
                        if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(className)) {
                            EventBusUtils.post(
                                ActivityChangedEvent(
                                    it.packageName.toString(),
                                    it.className.toString()
                                )
                            )
                        }
                    }
                }
            }
        })
        AccessibilityListenerService.startService()
    }

    /**
     * 追踪节点信息
     */
    private fun track(
        info: AccessibilityNodeInfo?,
        builder: StringBuilder,
        index: Int
    ) {
        info?.let {
            if (it.childCount == 0) {
                builder
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append(StringUtils.appendSpace(index)).append("child widget: ")
                    .append(it.className)
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append(StringUtils.appendSpace(index)).append("showDialog: ")
                    .append(it.canOpenPopup())
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append(StringUtils.appendSpace(index)).append("windowId: ").append(it.windowId)
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append(StringUtils.appendSpace(index)).append("Text: ").append(it.text)
                    .append(DevFinal.SYMBOL.NEW_LINE)
                    .append(StringUtils.appendSpace(index)).append("itViewId: ")
                    .append(it.viewIdResourceName)
            } else {
                for (i in 0 until it.childCount) {
                    track(it.getChild(i), builder, index + 1)
                }
            }
        }
    }
}