package afkt.gtpush

import com.therouter.TheRouter

/**
 * detail: App Router Path
 * @author Ttt
 */
object AppRouter {

    private const val GROUP = "gt_push"

    // ==================
    // =  Activity Path =
    // ==================

    const val PATH_MAIN_ACTIVITY = "/${GROUP}/activity/MainActivity"

    const val PATH_MESSAGE_ACTIVITY = "/${GROUP}/activity/MessageActivity"

    const val PATH_SECONDARY_ACTIVITY = "/${GROUP}/activity/SecondaryActivity"

    // =======
    // = 通用 =
    // =======

    /**
     * Router 跳转方法
     * @param path 跳转路径
     */
    private fun router(path: String) {
        TheRouter.build(path)
            .navigation()
    }

    // =============
    // = 对外公开方法 =
    // =============

    fun routerMainActivity() {
        router(PATH_MAIN_ACTIVITY)
    }

    fun routerMessageActivity() {
        router(PATH_MESSAGE_ACTIVITY)
    }

    fun routerSecondaryActivity() {
        router(PATH_SECONDARY_ACTIVITY)
    }
}