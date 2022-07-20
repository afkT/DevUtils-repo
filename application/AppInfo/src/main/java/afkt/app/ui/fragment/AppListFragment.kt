package afkt.app.ui.fragment

import afkt.app.R
import afkt.app.base.BaseFragment
import afkt.app.base.model.ActionEnum
import afkt.app.base.model.TypeEnum
import afkt.app.base.setDataStore
import afkt.app.databinding.FragmentAppBinding
import afkt.app.ui.adapter.AppListAdapter
import afkt.app.utils.AppListUtils
import afkt.app.utils.AppSearchUtils
import android.os.Bundle
import android.view.View
import com.tt.whorlviewlibrary.WhorlView
import dev.utils.app.ListViewUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.TextViewUtils
import dev.utils.app.ViewUtils
import dev.utils.common.HtmlUtils
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout

class AppListFragment : BaseFragment<FragmentAppBinding>() {

    companion object {
        fun get(type: TypeEnum): BaseFragment<FragmentAppBinding> {
            val fragment = AppListFragment()
            fragment.setDataStore(type)
            return fragment
        }
    }

    private var whorlView: WhorlView? = null

    override fun baseContentId() = R.layout.fragment_app

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.vidRefresh.setEnableLoadMore(false)
        whorlView = ViewUtils.findViewById(
            binding.vidStateSl.getView(ViewAssist.TYPE_ING),
            R.id.vid_whv
        )
        // 设置监听
        binding.vidStateSl.setListener(object : StateLayout.Listener {
            override fun onRemove(
                layout: StateLayout,
                type: Int,
                removeView: Boolean
            ) {
            }

            override fun onNotFound(
                layout: StateLayout,
                type: Int
            ) {
                if (type == ViewAssist.TYPE_SUCCESS) {
                    ViewUtils.reverseVisibilitys(true, binding.vidRefresh, binding.vidStateSl)
                    whorlView?.stop()
                    binding.vidRefresh.finishRefresh()
                }
            }

            override fun onChange(
                layout: StateLayout,
                type: Int,
                oldType: Int,
                view: View
            ) {
                if (ViewUtils.reverseVisibilitys(
                        type == ViewAssist.TYPE_SUCCESS,
                        binding.vidRefresh, binding.vidStateSl
                    )
                ) {
                    whorlView?.stop()
                    binding.vidRefresh.finishRefresh()
                } else {
                    if (type == ViewAssist.TYPE_ING) {
                        if (whorlView != null && !whorlView!!.isCircling) {
                            whorlView?.start()
                        }
                    } else {
                        whorlView?.stop()
                        // 无数据处理
                        if (type == ViewAssist.TYPE_EMPTY_DATA) {
                            binding.vidRefresh.finishRefresh()
                            val tips = if (dataStore.searchContent.isEmpty()) {
                                ResourceUtils.getString(R.string.str_search_noresult_tips_1)
                            } else {
                                ResourceUtils.getString(
                                    R.string.str_search_noresult_tips,
                                    HtmlUtils.addHtmlColor(dataStore.searchContent, "#359AFF")
                                )
                            }
                            TextViewUtils.setHtmlText(
                                view.findViewById(R.id.vid_tips_tv), tips
                            )
                        }
                    }
                }
            }
        })
        binding.vidStateSl.showIng()
        // 设置刷新事件
        binding.vidRefresh.setOnRefreshListener {
            AppListUtils.getAppLists(dataStore.typeEnum, true)
        }
    }

    override fun initObserve() {
        super.initObserve()
        // 搜索监听
        viewModel.searchEvent.observe(this) {
            when (it.action) {
                ActionEnum.COLLAPSE -> { // 搜索合并
                    if (it.type == dataStore.typeEnum) {
                        if (dataStore.searchContent.isNotEmpty()) { // 输入内容才刷新列表
                            dataStore.searchContent = ""
                            AppListUtils.getAppLists(it.type) // 加载列表
                        }
                    }
                }
                ActionEnum.EXPAND -> { // 搜索展开
                }
                ActionEnum.CONTENT -> { // 搜索输入内容
                    if (it.type == dataStore.typeEnum) {
                        dataStore.searchContent = it.content
                        AppListUtils.getAppLists(it.type) // 加载列表
                    }
                }
            }
        }
        // APP 列表监听
        viewModel.appObserve(this) {
            if (it.type == dataStore.typeEnum) {
                val lists = if (dataStore.searchContent.isEmpty()) {
                    it.lists
                } else {
                    AppSearchUtils.filterAppList(it.lists, dataStore.searchContent)
                }
                if (lists.isEmpty()) {
                    binding.vidStateSl.showEmptyData()
                } else {
                    binding.vidRefresh.setAdapter(AppListAdapter(lists))
                    binding.vidStateSl.showSuccess()
                }
            }
        }
        // Fragment 切换监听
        viewModel.fragmentChange.observe(this) {
            if (it == dataStore.typeEnum) {
                dataStore.searchContent = "" // 切换 Fragment 重置搜索内容
                AppListUtils.getAppLists(it) // 加载列表
            }
        }
        // 回到顶部
        viewModel.backTopEvent.observe(this) {
            if (it == dataStore.typeEnum) {
                ListViewUtils.smoothScrollToTop(binding.vidRefresh.getRecyclerView())
            }
        }
        // 刷新操作
        viewModel.refresh.observe(this) {
            if (it == dataStore.typeEnum) {
                binding.vidRefresh.getRefreshLayout()?.autoRefresh()
            }
        }
    }
}