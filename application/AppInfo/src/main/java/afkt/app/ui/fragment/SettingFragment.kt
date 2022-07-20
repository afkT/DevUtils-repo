package afkt.app.ui.fragment

import afkt.app.R
import afkt.app.base.BaseFragment
import afkt.app.databinding.FragmentSettingBinding
import afkt.app.ui.dialog.AppSortDialog
import afkt.app.ui.dialog.QuerySuffixDialog
import afkt.app.utils.AppListUtils
import afkt.app.utils.ProjectUtils
import afkt.app.utils.QuerySuffixUtils
import android.os.Bundle
import android.view.View
import dev.utils.app.*
import dev.utils.app.toast.ToastTintUtils

class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    override fun baseContentId() = R.layout.fragment_setting

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.vidAppsortLl.setOnClickListener {
            context?.let { AppSortDialog(viewModel, it).show() }
        }
        binding.vidScanapkLl.setOnClickListener {
            context?.let { QuerySuffixDialog(it).show() }
        }
        binding.vidStorageLl.setOnClickListener {
            if (VersionUtils.isR()) {
                AppUtils.startActivity(IntentUtils.getManageAppAllFilesAccessPermissionIntent())
            }
        }
        binding.vidResetLl.setOnClickListener {
            ProjectUtils.resetAppSortType() // 重置排序类型索引
            QuerySuffixUtils.reset() // 清空后缀
            AppListUtils.reset() // 清空应用列表
            selectAppSort() // 重置排序文案
            ToastTintUtils.success(ResourceUtils.getString(R.string.str_reset_desetting_suc))
        }
        selectAppSort()
    }

    override fun initObserve() {
        super.initObserve()
        viewModel.appSort.observe(viewLifecycleOwner) { selectAppSort() }
    }

    private fun selectAppSort() {
        // 设置选择排序文案
        binding.vidAppsortTv.text = ResourceUtils.getStringArray(
            R.array.array_app_sort
        )!![ProjectUtils.getAppSortType()]
    }

    override fun onResume() {
        super.onResume()

        if (
            ViewUtils.setVisibility(
                VersionUtils.isR(),
                binding.vidStorageLl
            )
        ) {
            TextViewUtils.setText(
                binding.vidStorageTv,
                ResourceUtils.getString(
                    if (PathUtils.isExternalStorageManager()) {
                        R.string.str_manage_storage_disable
                    } else {
                        R.string.str_manage_storage_enable
                    }
                )
            )
        }
    }
}