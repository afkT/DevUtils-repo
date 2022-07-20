package afkt.app.ui.activity

import afkt.app.R
import afkt.app.base.BaseActivity
import afkt.app.databinding.ActivityAppDetailsBinding
import afkt.app.ui.adapter.KeyValueAdapter
import afkt.app.utils.ExportUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import dev.kotlin.engine.log.log_e
import dev.utils.DevFinal
import dev.utils.app.AppUtils
import dev.utils.app.ClipboardUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.info.AppInfoItem
import dev.utils.app.info.AppInfoUtils
import dev.utils.app.info.KeyValue
import dev.utils.app.toast.ToastTintUtils

class AppDetailsActivity : BaseActivity<ActivityAppDetailsBinding>() {

    // APP 信息 Item
    private lateinit var appInfoItem: AppInfoItem

    override fun baseContentId(): Int = R.layout.activity_app_details

    override fun initValue() {
        super.initValue()
        try {
            val appInfo =
                AppInfoUtils.getAppInfoItem(intent.getStringExtra(DevFinal.STR.PACKNAME))
            appInfo?.let { appInfoItem = it }
        } catch (e: Exception) {
            log_e(throwable = e)
        }
        if (!this::appInfoItem.isInitialized) {
            ToastTintUtils.warning(ResourceUtils.getString(R.string.str_get_apkinfo_fail))
            finish()
            return
        }
        setSupportActionBar(binding.vidTb)
        supportActionBar?.let {
            // 给左上角图标的左边加上一个返回的图标
            it.setDisplayHomeAsUpEnabled(true)
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            it.setDisplayShowTitleEnabled(false)
            // 设置点击事件
            binding.vidTb.setNavigationOnClickListener { finish() }
        }
        // 获取 APP 信息
        val appInfoBean = appInfoItem.appInfoBean
        ViewHelper.get()
            .setImageDrawable(appInfoBean.appIcon, binding.vidAppIv) // 设置 app 图标
            .setText(appInfoBean.appName, binding.vidNameTv) // 设置 app 名
            .setText(appInfoBean.versionName, binding.vidVnameTv) // 设置 app 版本

        val lists = appInfoItem.listKeyValues
        lists.add(
            0,
            KeyValue.get(
                R.string.str_app_market,
                ResourceUtils.getString(R.string.str_goto_app_market)
            )
        )
        lists.add(
            1,
            KeyValue.get(
                R.string.str_app_details_setting,
                ResourceUtils.getString(R.string.str_goto_app_details_setting)
            )
        )
        binding.vidRv.adapter = KeyValueAdapter(lists)
            .setItemCallback(object : dev.callback.DevItemClickCallback<KeyValue>() {
                override fun onItemClick(
                    value: KeyValue?,
                    param: Int
                ) {
                    if (param == 0) {
                        if (!AppUtils.launchAppDetails(appInfoBean.appPackName, "")) {
                            ToastTintUtils.error(ResourceUtils.getString(R.string.str_operate_fail))
                        }
                    } else if (param == 1) {
                        if (AppUtils.isInstalledApp(appInfoBean.appPackName)) {
                            AppUtils.launchAppDetailsSettings(appInfoBean.appPackName)
                        } else {
                            ToastTintUtils.error(ResourceUtils.getString(R.string.str_app_not_exist))
                        }
                    } else {
                        value?.let {
                            val txt = it.toString()
                            // 复制到剪切板
                            ClipboardUtils.copyText(txt)
                            // 进行提示
                            ToastTintUtils.success(ResourceUtils.getString(R.string.str_copy_suc) + ", " + txt)
                        }
                    }
                }
            })
        binding.vidOpenAppTv.setOnClickListener(this)
        binding.vidUninstallTv.setOnClickListener(this)
    }

    // ===========
    // = OnClick =
    // ===========

    override fun onClick(v: View) {
        if (!AppUtils.isInstalledApp(appInfoItem.appInfoBean.appPackName)) {
            ToastTintUtils.error(ResourceUtils.getString(R.string.str_app_not_exist))
            return
        }
        when (v.id) {
            R.id.vid_open_app_tv -> AppUtils.launchApp(appInfoItem.appInfoBean.appPackName)
            R.id.vid_uninstall_tv -> AppUtils.uninstallApp(appInfoItem.appInfoBean.appPackName)
        }
    }

    // ========
    // = Menu =
    // ========

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bar_menu_app_info, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != R.id.vid_menu_export_app_msg) {
            if (!AppUtils.isInstalledApp(appInfoItem.appInfoBean.appPackName)) {
                ToastTintUtils.error(ResourceUtils.getString(R.string.str_app_not_exist))
                return false
            }
        }
        when (item.itemId) {
            R.id.vid_menu_share -> {
                ExportUtils.shareApp(appInfoItem)
            }
            R.id.vid_menu_export_app -> {
                ExportUtils.exportApp(appInfoItem)
            }
            R.id.vid_menu_export_app_msg -> {
                ExportUtils.exportInfo(appInfoItem)
            }
        }
        return true
    }
}