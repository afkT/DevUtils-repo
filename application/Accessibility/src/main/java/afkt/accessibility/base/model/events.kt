package afkt.accessibility.base.model

/**
 * detail: Activity 改变通知事件
 */
class ActivityChangedEvent(
    val packageName: String,
    val className: String
)