package afkt.db.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.ViewDataBinding
import dev.base.core.interfaces.IDevBase
import dev.base.simple.ActivityVMType
import dev.base.simple.DevSimpleActivity
import dev.base.simple.contracts.binding.BindingActivityView

/**
 * detail: Activity MVVM 基类
 * @author Ttt
 */
open class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> :
    DevSimpleActivity<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int = IDevBase.NONE,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null
    ) : super(
        bindLayoutId, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile
    )

    constructor(
        bindLayoutView: BindingActivityView?,
        bindViewModelId: Int = IDevBase.NONE,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null
    ) : super(
        bindLayoutView, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile
    )

    // ============
    // = override =
    // ============

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 通用 Enable edge to edge【适配 API 35+】
        commonEnableEdgeToEdge()
    }
}

/**
 * 通用 Enable edge to edge【适配 API 35+】
 */
fun BaseActivity<*, *>.commonEnableEdgeToEdge() {
    enableEdgeToEdge()
    // 给 view 设置 insets, 使得 view 不会被 system bars 遮挡
    ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(
            systemBars.left, systemBars.top,
            systemBars.right, systemBars.bottom
        )
        insets
    }
}