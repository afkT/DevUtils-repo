package afkt.app.ui.fragment

import afkt.app.R
import afkt.app.base.BaseFragment
import afkt.app.base.model.DeviceInfoBean
import afkt.app.base.model.PathConfig
import afkt.app.base.model.TypeEnum
import afkt.app.base.setDataStore
import afkt.app.databinding.FragmentInfoBinding
import afkt.app.ui.adapter.InfoAdapter
import afkt.app.utils.ExportUtils
import afkt.app.utils.ProjectUtils
import android.os.Bundle
import android.view.View

class InfoFragment : BaseFragment<FragmentInfoBinding>() {

    companion object {
        fun get(type: TypeEnum): BaseFragment<FragmentInfoBinding> {
            val fragment = InfoFragment()
            fragment.setDataStore(type)
            return fragment
        }
    }

    private val mAdapter: InfoAdapter = InfoAdapter()

    override fun baseContentId() = R.layout.fragment_info

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setEnableRefresh(false)
            .setEnableLoadMore(false)
        binding.root.setAdapter(mAdapter)

        viewModel.infoObserve(viewLifecycleOwner) {
            if (it.type == dataStore.typeEnum) {
                mAdapter.setDataList(it.lists)
            }
        }
        viewModel.exportEvent.observe(viewLifecycleOwner) {
            if (it == dataStore.typeEnum) {
                if (mAdapter.isDataNotEmpty) {
                    val content: String? = DeviceInfoBean.jsonString(mAdapter.dataList)
                    val fileName =
                        if (TypeEnum.DEVICE_INFO == it) "device_info.txt" else "screen_info.txt"
                    // 导出数据
                    ExportUtils.exportInfo(
                        fileName, PathConfig.AEP_PATH, content
                    )
                }
            }
        }
        when (dataStore.typeEnum) {
            TypeEnum.DEVICE_INFO -> ProjectUtils.getDeviceInfo(viewModel)
            TypeEnum.SCREEN_INFO -> ProjectUtils.getScreenInfo(viewModel)
            else -> {}
        }
    }
}