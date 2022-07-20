package afkt.app.base

import afkt.app.base.model.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    // 应用排序
    val appSort = MutableLiveData<Boolean>()

    // 导出设备、屏幕信息事件
    val exportEvent = MutableLiveData<TypeEnum>()

    // 搜索事件
    var searchEvent = MutableLiveData<SearchEvent>()

    // 回到顶部事件
    val backTopEvent = MutableLiveData<TypeEnum>()

    // 刷新操作
    val refresh = MutableLiveData<TypeEnum>()

    // Fragment 切换
    val fragmentChange = MutableLiveData<TypeEnum>()

    // 文件扫描
    val sdCardScan = MutableLiveData<ArrayList<FileApkItem>>()

    // =

    // 设备信息
    val deviceInfo = MutableLiveData<DeviceInfo>()

    // 屏幕信息
    val screenInfo = MutableLiveData<DeviceInfo>()

    // 用户、系统、全部 APP 类型
    var userApp = MutableLiveData<AppListBean>()
    var systemApp = MutableLiveData<AppListBean>()
    var allApp = MutableLiveData<AppListBean>()

    fun infoObserve(
        owner: LifecycleOwner,
        observer: Observer<DeviceInfo>
    ) {
        deviceInfo.observe(owner, observer)
        screenInfo.observe(owner, observer)
    }

    fun appObserve(
        owner: LifecycleOwner,
        observer: Observer<AppListBean>
    ) {
        userApp.observe(owner, observer)
        systemApp.observe(owner, observer)
        allApp.observe(owner, observer)
    }

    fun postAppSort() {
        appSort.postValue(true)
    }

    fun postExportEvent(value: TypeEnum) {
        exportEvent.postValue(value)
    }

    fun postSearch(value: SearchEvent) {
        searchEvent.postValue(value)
    }

    fun postBackTop(value: TypeEnum) {
        backTopEvent.postValue(value)
    }

    fun postRefresh(value: TypeEnum) {
        refresh.postValue(value)
    }

    fun postFragmentChange(value: TypeEnum) {
        fragmentChange.postValue(value)
    }

    fun postSDCardScan(value: ArrayList<FileApkItem>) {
        sdCardScan.postValue(value)
    }

    fun postDeviceInfo(value: DeviceInfo) {
        deviceInfo.postValue(value)
    }

    fun postScreenInfo(value: DeviceInfo) {
        screenInfo.postValue(value)
    }

    fun postUserApp(value: AppListBean) {
        userApp.postValue(value)
    }

    fun postSystemApp(value: AppListBean) {
        systemApp.postValue(value)
    }

    fun postAllApp(value: AppListBean) {
        allApp.postValue(value)
    }
}

// =================
// = 全局 ViewModel =
// =================

class GlobalViewModel : ViewModel() {

    // 文件删除
    val fileDelete = MutableLiveData<Boolean>()

    fun postFileDelete() {
        fileDelete.postValue(true)
    }
}