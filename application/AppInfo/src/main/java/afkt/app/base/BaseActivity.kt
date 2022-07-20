package afkt.app.base

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import dev.DevUtils
import dev.base.expand.viewbinding.DevBaseViewBindingActivity
import dev.base.utils.ViewModelUtils

abstract class BaseActivity<VB : ViewBinding> : DevBaseViewBindingActivity<VB>() {

    val viewModel by viewModels<AppViewModel>()

    var globalViewModel: GlobalViewModel? = ViewModelUtils.getAppViewModel(
        DevUtils.getApplication(), GlobalViewModel::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOrder()
    }

    // ===================
    // = IDevBaseContent =
    // ===================

    override fun baseContentView(): View? = null
}