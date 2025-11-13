package afkt.db

import com.therouter.TheRouter

/**
 * detail: App Router Path
 * @author Ttt
 */
object AppRouter {

    private const val GROUP = "app_db"

    // ==================
    // =  Activity Path =
    // ==================

    const val PATH_MAIN_ACTIVITY = "/${GROUP}/activity/MainActivity"

    const val PATH_GREEN_DAO_ACTIVITY = "/${GROUP}/activity/GreenDaoActivity"

    const val PATH_ROOM_ACTIVITY = "/${GROUP}/activity/RoomActivity"

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

    fun routerGreenDaoActivity() {
        router(PATH_GREEN_DAO_ACTIVITY)
    }

    fun routerRoomActivity() {
        router(PATH_ROOM_ACTIVITY)
    }
}