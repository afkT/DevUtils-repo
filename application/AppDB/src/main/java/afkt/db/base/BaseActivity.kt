package afkt.db.base

import afkt.db.R
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.therouter.TheRouter
import com.therouter.router.Autowired
import dev.base.expand.content.DevBaseContentViewBindingActivity
import dev.utils.DevFinal
import dev.utils.app.ViewUtils

/**
 * detail: Base ViewBinding 基类
 * @author Ttt
 */
abstract class BaseActivity<VB : ViewBinding> :
    DevBaseContentViewBindingActivity<VB>() {

    @JvmField // Context
    protected var mContext: Context? = null

    @JvmField // Activity
    protected var mActivity: Activity? = null

    // ToolBar
    var toolbar: Toolbar? = null

    @JvmField
    @Autowired(name = DevFinal.STR.TITLE)
    var moduleTitle: String? = null

    override fun baseLayoutView(): View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取 Context、Activity
        mContext = this
        mActivity = this
        // 内部初始化
        innerInitialize()
        // 是否需要 ToolBar
        if (isToolBar()) {
            // 初始化 ToolBar
            initToolBar()
            // 设置 ToolBar 标题
            toolbar?.title = moduleTitle
        }
        // 初始化顺序 ( 按顺序调用方法 )
        initOrder()
    }

    // ===========
    // = ToolBar =
    // ===========

    /**
     * 是否需要 ToolBar
     */
    open fun isToolBar(): Boolean {
        return true
    }

    /**
     * 初始化 ToolBar
     */
    private fun initToolBar() {
        val titleView = ViewUtils.inflate(this, R.layout.base_toolbar, null)
        toolbar = titleView.findViewById(R.id.vid_tb)
        contentAssist.addTitleView(titleView)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            // 给左上角图标的左边加上一个返回的图标
            it.setDisplayHomeAsUpEnabled(true)
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            it.setDisplayShowTitleEnabled(false)
        }
        // 设置点击事件
        toolbar?.setNavigationOnClickListener { finish() }
    }

    // ============
    // = 内部初始化 =
    // ============

    private fun innerInitialize() {
        try {
            TheRouter.inject(this)
        } catch (ignored: Exception) {
        }
    }

    // =======
    // = 通用 =
    // =======

    /**
     * Router 跳转方法
     * @param text 标题
     * @param path Router Path
     */
    fun routerActivity(
        text: String,
        path: String
    ) {
        TheRouter.build(path)
            .withString(DevFinal.STR.TITLE, text)
            .navigation(this)
    }
}