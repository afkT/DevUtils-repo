package afkt.app.ui.activity

import afkt.app.R
import afkt.app.base.BaseActivity
import afkt.app.databinding.ActivityApkDetailsBinding
import afkt.app.ui.adapter.KeyValueAdapter
import afkt.app.utils.ExportUtils
import android.Manifest
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import dev.callback.DevItemClickCallback
import dev.engine.permission.IPermissionEngine
import dev.kotlin.engine.log.log_d
import dev.kotlin.engine.log.log_e
import dev.kotlin.engine.permission.permission_request
import dev.utils.DevFinal
import dev.utils.app.AppUtils
import dev.utils.app.ClipboardUtils
import dev.utils.app.IntentUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.info.ApkInfoItem
import dev.utils.app.info.AppInfoUtils
import dev.utils.app.info.KeyValue
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.FileUtils

class ApkDetailsActivity : BaseActivity<ActivityApkDetailsBinding>() {

    // APK 信息 Item
    private lateinit var apkInfoItem: ApkInfoItem

    override fun baseContentId(): Int = R.layout.activity_apk_details

    override fun initValue() {
        super.initValue()
        try {
            val apkInfo = AppInfoUtils.getApkInfoItem(intent.getStringExtra(DevFinal.STR.URI))
            apkInfo?.let { apkInfoItem = it }
        } catch (e: Exception) {
            log_e(throwable = e)
        }
        if (!this::apkInfoItem.isInitialized) {
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
        val appInfoBean = apkInfoItem.appInfoBean
        ViewHelper.get()
            .setImageDrawable(appInfoBean.appIcon, binding.vidAppIv) // 设置 app 图标
            .setText(appInfoBean.appName, binding.vidNameTv) // 设置 app 名
            .setText(appInfoBean.versionName, binding.vidVnameTv) // 设置 app 版本

        binding.vidRv.adapter = KeyValueAdapter(apkInfoItem.listKeyValues)
            .setItemCallback(object : DevItemClickCallback<KeyValue>() {
                override fun onItemClick(
                    value: KeyValue?,
                    param: Int
                ) {
                    value?.let {
                        val txt = it.toString()
                        // 复制到剪切板
                        ClipboardUtils.copyText(txt)
                        // 进行提示
                        ToastTintUtils.success(ResourceUtils.getString(R.string.str_copy_suc) + ", " + txt)
                    }
                }
            })
    }

    override fun initListener() {
        super.initListener()
        // 安装应用
        binding.vidInstallTv.setOnClickListener {
            val sourceDir = apkInfoItem.appInfoBean.sourceDir
            if (FileUtils.isFileExists(sourceDir)) {
                // Android 8.0 以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (packageManager.canRequestPackageInstalls()) {
                        AppUtils.installApp(sourceDir) // 安装 APK
                    } else {
                        permission_request(
                            permissions = arrayOf(
                                Manifest.permission.REQUEST_INSTALL_PACKAGES
                            ),
                            callback = object : IPermissionEngine.Callback {
                                override fun onGranted() {
                                    AppUtils.installApp(sourceDir) // 安装 APK
                                }

                                override fun onDenied(
                                    grantedList: List<String>,
                                    deniedList: List<String>,
                                    notFoundList: List<String>
                                ) {
                                    val builder = StringBuilder()
                                        .append("申请通过的权限")
                                        .append(grantedList.toTypedArray().contentToString())
                                        .append(DevFinal.SYMBOL.NEW_LINE)
                                        .append("拒绝的权限").append(deniedList.toString())
                                        .append(DevFinal.SYMBOL.NEW_LINE)
                                        .append("未找到的权限").append(notFoundList.toString())
                                    if (deniedList.isNotEmpty()) {
                                        log_d(message = builder.toString())
                                        ToastTintUtils.info(ResourceUtils.getString(R.string.str_install_request_tips))
                                        // 跳转设置页面, 开启安装未知应用权限
                                        startActivity(IntentUtils.getLaunchAppInstallPermissionSettingsIntent())
                                    } else {
                                        onGranted()
                                    }
                                }
                            }
                        )
                    }
                } else { // 安装 APK
                    AppUtils.installApp(sourceDir)
                }
            } else {
                ToastTintUtils.warning(ResourceUtils.getString(R.string.str_file_not_exist))
            }
        }
        // 删除安装包
        binding.vidDeleteTv.setOnClickListener {
            val sourceDir = apkInfoItem.appInfoBean.sourceDir
            if (FileUtils.isFileExists(sourceDir)) {
                FileUtils.deleteFile(sourceDir)
                globalViewModel?.postFileDelete()
                ToastTintUtils.success(ResourceUtils.getString(R.string.str_delete_suc))
            } else {
                ToastTintUtils.warning(ResourceUtils.getString(R.string.str_file_not_exist))
            }
        }
    }

    // ========
    // = Menu =
    // ========

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bar_menu_apk_info, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.vid_menu_share -> {
                ExportUtils.shareApp(apkInfoItem)
            }
            R.id.vid_menu_export_apk -> {
                ExportUtils.exportApp(apkInfoItem)
            }
            R.id.vid_menu_export_apk_msg -> {
                ExportUtils.exportInfo(apkInfoItem)
            }
        }
        return true
    }
}