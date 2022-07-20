package afkt.app.utils

import afkt.app.base.model.FileApkItem
import dev.utils.app.info.AppInfoBean
import dev.utils.common.StringUtils

/**
 * detail: 应用搜索工具类
 * @author Ttt
 */
object AppSearchUtils {

    /**
     * 筛选列表数据
     * @param lists 数据源
     * @param searchContent 筛选关键字
     */
    fun filterAppList(
        lists: List<AppInfoBean>,
        searchContent: String
    ): ArrayList<AppInfoBean> {
        val filter = ArrayList<AppInfoBean>()
        for (item in lists) {
            if (StringUtils.isContains(
                    true,
                    searchContent, item.appName, item.appPackName
                )
            ) {
                filter.add(item)
            }
        }
        return filter
    }

    /**
     * 筛选列表数据
     * @param lists 数据源
     * @param searchContent 筛选关键字
     */
    fun filterApkList(
        lists: List<FileApkItem>,
        searchContent: String
    ): ArrayList<FileApkItem> {
        val filter = ArrayList<FileApkItem>()
        for (item in lists) {
            if (StringUtils.isContains(
                    true,
                    searchContent, item.appInfoBean.appName, item.appInfoBean.appPackName
                )
            ) {
                filter.add(item)
            }
        }
        return filter
    }
}