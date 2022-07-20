package afkt.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import dev.DevUtils
import dev.base.expand.viewbinding.DevBaseViewBindingFragment
import dev.base.utils.ViewModelUtils

abstract class BaseFragment<VB : ViewBinding> : DevBaseViewBindingFragment<VB>() {

    val dataStore = AppDataStore()

    val viewModel by activityViewModels<AppViewModel>()

    var globalViewModel: GlobalViewModel? = ViewModelUtils.getAppViewModel(
        DevUtils.getApplication(), GlobalViewModel::class.java
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataStore.initDataStore(arguments = arguments)
        initOrder()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    // ===================
    // = IDevBaseContent =
    // ===================

    override fun baseContentView(): View? = null
}