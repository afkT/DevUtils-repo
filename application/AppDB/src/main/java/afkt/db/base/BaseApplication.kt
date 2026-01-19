package afkt.db.base

import androidx.multidex.MultiDexApplication
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.therouter.TheRouter
import dev.DevUtils
import dev.engine.DevEngine

/**
 * detail: Base Application
 * @author Ttt
 */
class BaseApplication : MultiDexApplication() {

    // 日志 TAG
    val TAG = "AppDB_TAG"

    override fun onCreate() {
        super.onCreate()

        // Router 初始化
        TheRouter.init(this)

        // DevUtils Debug 开发配置
        DevUtils.debugDevelop(TAG)

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)

        // 初始化下拉刷新框架
        initializeSmartRefreshLayout()
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 初始化下拉刷新框架
     */
    private fun initializeSmartRefreshLayout() {
        // 设置全局的 Header 构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            return@setDefaultRefreshHeaderCreator ClassicsHeader(context)
        }
        // 设置全局的 Footer 构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            return@setDefaultRefreshFooterCreator ClassicsFooter(context)
        }
    }
}