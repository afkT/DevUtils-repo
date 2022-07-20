package afkt.app.base

import afkt.app.BuildConfig
import afkt.app.R
import afkt.app.base.model.AppConfig
import android.view.View
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import dev.DevUtils
import dev.engine.DevEngine
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout

class BaseApplication : MultiDexApplication(),
    ViewModelStoreOwner {

    // ViewModelStore
    private lateinit var mAppViewModelStore: ViewModelStore

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun onCreate() {
        super.onCreate()

        mAppViewModelStore = ViewModelStore()

        if (BuildConfig.DEBUG) {
            // 初始化 Logger 配置
            DevLogger.initialize(
                LogConfig()
                    .logLevel(LogLevel.DEBUG)
                    .tag(AppConfig.LOG_TAG)
                    .sortLog(true)
                    .methodCount(0)
            )
            // 打开 lib 内部日志 - 线上环境, 不调用方法
            DevUtils.openLog()
            DevUtils.openDebug()
        }

        // DevEngine 完整初始化
        DevEngine.completeInitialize(this)

        // 全局状态布局配置
        val global = StateLayout.Global(object : StateLayout.Listener {
            override fun onRemove(
                layout: StateLayout,
                type: Int,
                removeView: Boolean
            ) {
                if (removeView) layout.gone()
            }

            override fun onNotFound(
                layout: StateLayout,
                type: Int
            ) {
                layout.gone()
            }

            override fun onChange(
                layout: StateLayout,
                type: Int,
                oldType: Int,
                view: View
            ) {
                when (type) {
                    ViewAssist.TYPE_ING -> {
                    }
                    ViewAssist.TYPE_EMPTY_DATA -> {
                    }
                }
            }
        })
            .register(ViewAssist.TYPE_ING, R.layout.state_layout_ing)
            .register(ViewAssist.TYPE_EMPTY_DATA, R.layout.state_layout_no_data)
        // 设置全局配置
        StateLayout.setGlobal(global)
    }
}