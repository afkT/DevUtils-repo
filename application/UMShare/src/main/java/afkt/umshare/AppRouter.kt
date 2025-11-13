package afkt.umshare

import com.therouter.TheRouter

/**
 * detail: App Router Path
 * @author Ttt
 */
object AppRouter {

    private const val GROUP = "um_share"

    // ==================
    // =  Activity Path =
    // ==================

    const val PATH_MAIN_ACTIVITY = "/${GROUP}/activity/MainActivity"

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
}