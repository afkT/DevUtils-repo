package afkt.app.ui.fragment

import afkt.app.R
import afkt.app.base.BaseFragment
import afkt.app.base.model.ActionEnum
import afkt.app.base.model.FileApkItem
import afkt.app.base.model.TypeEnum
import afkt.app.databinding.FragmentAppBinding
import afkt.app.ui.adapter.ApkListAdapter
import afkt.app.utils.AppSearchUtils
import afkt.app.utils.ScanSDCardUtils
import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tt.whorlviewlibrary.WhorlView
import dev.callback.DevCallback
import dev.engine.permission.IPermissionEngine
import dev.kotlin.engine.log.log_d
import dev.kotlin.engine.log.log_e
import dev.kotlin.engine.permission.permission_request
import dev.utils.DevFinal
import dev.utils.app.ListViewUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.TextViewUtils
import dev.utils.app.ViewUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils
import dev.utils.common.HtmlUtils
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout

class ScanSDCardFragment : BaseFragment<FragmentAppBinding>() {

    private var whorlView: WhorlView? = null

    private var type = TypeEnum.QUERY_APK

    private val mAdapter: ApkListAdapter = ApkListAdapter()

    override fun baseContentId(): Int = R.layout.fragment_app

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        // 设置扫描回调
        ScanSDCardUtils.instance.setCallback(object : DevCallback<ArrayList<FileApkItem>>() {
            override fun callback(value: ArrayList<FileApkItem>?) {
                value?.let { viewModel.postSDCardScan(it) }
            }
        })
        binding.vidRefresh.setEnableLoadMore(false)
        binding.vidRefresh.setAdapter(mAdapter)

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
            requestReadWrite(true)
        }
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    try {
                        val position = viewHolder.adapterPosition
                        FileUtils.deleteFile(mAdapter.getDataItem(position).uri)
                        mAdapter.removeDataAt(position)
                    } catch (e: Exception) {
                        log_e(throwable = e)
                    }
                    if (mAdapter.isDataEmpty) {
                        binding.vidStateSl.showEmptyData()
                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.vidRefresh.getRecyclerView())
    }

    override fun initObserve() {
        super.initObserve()

        // 搜索监听
        viewModel.searchEvent.observe(this) {
            when (it.action) {
                ActionEnum.COLLAPSE -> { // 搜索合并
                    if (it.type == type) {
                        if (dataStore.searchContent.isNotEmpty()) { // 输入内容才刷新列表
                            dataStore.searchContent = ""
                            requestReadWrite()
                        }
                    }
                }
                ActionEnum.EXPAND -> { // 搜索展开
                }
                ActionEnum.CONTENT -> { // 搜索输入内容
                    if (it.type == type) {
                        dataStore.searchContent = it.content
                        requestReadWrite()
                    }
                }
            }
        }
        // Fragment 切换监听
        viewModel.fragmentChange.observe(this) {
            if (it == type) {
                dataStore.searchContent = "" // 切换 Fragment 重置搜索内容
                requestReadWrite()
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
        // 文件扫描
        viewModel.sdCardScan.observe(this) {
            val lists = if (dataStore.searchContent.isEmpty()) {
                it
            } else {
                AppSearchUtils.filterApkList(it, dataStore.searchContent)
            }
            if (lists.isEmpty()) {
                binding.vidStateSl.showEmptyData()
            } else {
                mAdapter.setDataList(lists)
                binding.vidStateSl.showSuccess()
            }
        }
        // 文件删除
        globalViewModel?.let {
            it.fileDelete.observe(this) {
                binding.vidRefresh.getRecyclerView()?.adapter?.notifyDataSetChanged()
            }
        }
    }

    /**
     * 请求读写权限
     */
    private fun requestReadWrite() {
        requestReadWrite(false)
    }

    /**
     * 请求读写权限
     * @param refresh 是否刷新
     */
    private fun requestReadWrite(refresh: Boolean) {
        activity?.permission_request(
            permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            callback = object : IPermissionEngine.Callback {
                override fun onGranted() {
                    ScanSDCardUtils.instance.query(refresh) // 扫描 SDCard
                }

                override fun onDenied(
                    grantedList: List<String>,
                    deniedList: List<String>,
                    notFoundList: List<String>
                ) {
                    val builder = StringBuilder()
                        .append("申请通过的权限").append(grantedList.toTypedArray().contentToString())
                        .append(DevFinal.SYMBOL.NEW_LINE)
                        .append("拒绝的权限").append(deniedList.toString())
                        .append(DevFinal.SYMBOL.NEW_LINE)
                        .append("未找到的权限").append(notFoundList.toString())
                    if (deniedList.isNotEmpty()) {
                        log_d(message = builder.toString())
                        ToastTintUtils.info(ResourceUtils.getString(R.string.str_read_write_request_tips))
                    } else {
                        onGranted()
                    }
                }
            }
        )
    }
}