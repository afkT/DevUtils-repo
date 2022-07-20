package dev.standard.catalog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.utils.common.FileUtils;

/**
 * detail: 目录生成配置
 * @author Ttt
 */
final class Config {

    private Config() {
    }

    // 当前目录
    public static final String USER_DIR      = System.getProperty("user.dir");
    // 根目录
    public static final String DIR           = FileUtils.getFile(USER_DIR).getParent();
    // DevUtils 根目录
    public static final String DEV_UTILS_DIR = DIR + File.separator + "DevUtils";

    // ===========
    // = Android =
    // ===========

    // Android 文件名
    public static final String              ANDROID_DIR_NAME   = "Android";
    // Android 项目本地路径
    public static final String              ANDROID_LOCAL_PATH = DIR + File.separator + ANDROID_DIR_NAME;
    // Android 文件目录注释
    public static final Map<String, String> sAndroidCatalogMap = new HashMap<>();

    // ========
    // = Java =
    // ========

    // Java 文件名
    public static final String              JAVA_DIR_NAME   = "Java";
    // Java 项目本地路径
    public static final String              JAVA_LOCAL_PATH = DIR + File.separator + JAVA_DIR_NAME;
    // Java 文件目录注释
    public static final Map<String, String> sJavaCatalogMap = new HashMap<>();

    // ============
    // = DevUtils =
    // ============

    // ================
    // = DevUtils Lib =
    // ================

    // DevUtils Lib 文件名
    public static final String              DEV_UTILS_DIR_NAME      = "lib";
    // DevUtils Lib 项目本地路径
    public static final String              DEV_UTILS_LOCAL_PATH    = DEV_UTILS_DIR + File.separator + DEV_UTILS_DIR_NAME;
    // DevUtils Lib 文件目录注释
    public static final Map<String, String> sDevUtilsCatalogMap     = new HashMap<>();
    // DevUtils Lib 忽略目录
    public static final List<String>        sDevUtilsIgnoreCatalogs = new ArrayList<>();

    // ========================
    // = DevUtils Application =
    // ========================

    // DevUtils Application 文件名
    public static final String              DEV_UTILS_APPLICATION_DIR_NAME   = "application";
    // DevUtils Application 项目本地路径
    public static final String              DEV_UTILS_APPLICATION_LOCAL_PATH = DEV_UTILS_DIR + File.separator + DEV_UTILS_APPLICATION_DIR_NAME;
    // DevUtils Application 文件目录注释
    public static final Map<String, String> sDevUtilsApplicationCatalogMap   = new HashMap<>();

    // =================
    // = DevUtils-repo =
    // =================

    // =============================
    // = DevUtils-repo Application =
    // =============================

    // DevUtils-repo Application 文件名
    public static final String              DEV_UTILS_REPO_APPLICATION_DIR_NAME   = "application";
    // DevUtils-repo Application 项目本地路径
    public static final String              DEV_UTILS_REPO_APPLICATION_LOCAL_PATH = DEV_UTILS_DIR + File.separator + DEV_UTILS_REPO_APPLICATION_DIR_NAME;
    // DevUtils-repo Application 文件目录注释
    public static final Map<String, String> sDevUtilsRepoApplicationCatalogMap    = new HashMap<>();

    // ========================
    // = DevUtils-repo Module =
    // ========================

    // DevUtils-repo Module 文件名
    public static final String              DEV_UTILS_REPO_MODULE_DIR_NAME    = "module";
    // DevUtils-repo Module 项目本地路径
    public static final String              DEV_UTILS_REPO_MODULE_LOCAL_PATH  = USER_DIR + File.separator + DEV_UTILS_REPO_MODULE_DIR_NAME;
    // DevUtils-repo Module 文件目录注释
    public static final Map<String, String> sDevUtilsRepoModuleCatalogMap     = new HashMap<>();
    // DevUtils-repo Module 忽略目录
    public static final List<String>        sDevUtilsRepoModuleIgnoreCatalogs = new ArrayList<>();

    // =============================
    // = DevUtils-repo Interesting =
    // =============================

    // DevUtils-repo Interesting 文件名
    public static final String              DEV_UTILS_REPO_INTERESTING_DIR_NAME   = "interesting";
    // DevUtils-repo Interesting 项目本地路径
    public static final String              DEV_UTILS_REPO_INTERESTING_LOCAL_PATH = USER_DIR + File.separator + DEV_UTILS_REPO_INTERESTING_DIR_NAME;
    // DevUtils-repo Interesting 文件目录注释
    public static final Map<String, String> sDevUtilsRepoInterestingCatalogMap    = new HashMap<>();

    static {

        // ===========
        // = Android =
        // ===========

        sAndroidCatalogMap.put("Android", "根目录");
        sAndroidCatalogMap.put(".360RePlugin", "Android 插件化开发 - 360 RePlugin 框架");
        sAndroidCatalogMap.put(".AndroidVideoClip", "Android 视频裁剪 (含裁剪 View)");
        sAndroidCatalogMap.put(".AutoLockScreenPro", "AutoLockScreenPro - 推送自动锁屏");
        sAndroidCatalogMap.put(".BuglyHotfix", "Android 热修复 - Bugly");
        sAndroidCatalogMap.put(".PlaySeekbar", "视频裁剪自定义 View");
        sAndroidCatalogMap.put(".PushHandlerPro", "Android 点击推送逻辑处理、页面跳转判断");
        sAndroidCatalogMap.put(".RecordVideo", "录制视频 View (拍照 + 视频)");
        sAndroidCatalogMap.put(".RecordView", "录制进步式 View");
        sAndroidCatalogMap.put(".SophixPro", "Android - 热修复 Sophix");

        // ========
        // = Java =
        // ========

        sJavaCatalogMap.put("Java", "根目录");
        sJavaCatalogMap.put(".VideoClip", "Java 实现 MP4 裁剪功能");

        // ============
        // = DevUtils =
        // ============

        // ================
        // = DevUtils Lib =
        // ================

        sDevUtilsCatalogMap.put("lib", "根目录");
        sDevUtilsCatalogMap.put(".DevApp", "Android 工具类库");
        sDevUtilsCatalogMap.put(".DevAssist", "封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等");
        sDevUtilsCatalogMap.put(".DevBase", "Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库");
        sDevUtilsCatalogMap.put(".DevBaseMVVM", "MVVM ( ViewDataBinding + ViewModel ) 基类库");
        sDevUtilsCatalogMap.put(".DevEngine", "第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用");
        sDevUtilsCatalogMap.put(".DevHttpCapture", "OkHttp 抓包工具库");
        sDevUtilsCatalogMap.put(".DevHttpManager", "OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )");
        sDevUtilsCatalogMap.put(".DevJava", "Java 工具类库 ( 不依赖 android api )");
        sDevUtilsCatalogMap.put(".DevRetrofit", "Retrofit + Kotlin Coroutines 封装");
        sDevUtilsCatalogMap.put(".DevWidget", "自定义 View UI 库");
        sDevUtilsCatalogMap.put(".Environment", "Android 环境配置切换库");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironment", "环境切换可视化 UI 操作");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironmentBase", "注解类、实体类、监听事件等通用基础");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironmentCompiler", "Debug ( 打包 / 编译 ) 生成实现代码");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironmentCompilerRelease", "Release ( 打包 / 编译 ) 生成实现代码");
        sDevUtilsCatalogMap.put(".HttpCapture", "OkHttp 抓包工具库 ( 可视化功能 )");
        sDevUtilsCatalogMap.put(".HttpCapture.DevHttpCaptureCompiler", "Debug ( 打包 / 编译 ) 实现代码");
        sDevUtilsCatalogMap.put(".HttpCapture.DevHttpCaptureCompilerRelease", "Release ( 打包 / 编译 ) 实现代码");
        sDevUtilsCatalogMap.put(".LocalModules", "本地 Module lib ( 非发布库 )");
        sDevUtilsCatalogMap.put(".LocalModules.DevBase2", "Base 基础代码 ( 非基类库 )");
        sDevUtilsCatalogMap.put(".LocalModules.DevOther", "第三方库封装、以及部分特殊工具类等, 方便 copy 封装类使用");
        sDevUtilsCatalogMap.put(".LocalModules.DevSKU", "商品 SKU 组合封装实现");
        sDevUtilsCatalogMap.put(".LocalModules.DevComponent", "【100% Kotlin 实现 Android 项目组件化示例代码】");
        sDevUtilsCatalogMap.put(".LocalModules.DevComponent.lib_utils", "/core/libs/lib_utils");

        // =======================
        // = DevUtils Lib 忽略目录 =
        // =======================

        sDevUtilsIgnoreCatalogs.add("DevApp");
        sDevUtilsIgnoreCatalogs.add("DevAssist");
        sDevUtilsIgnoreCatalogs.add("DevBase");
        sDevUtilsIgnoreCatalogs.add("DevBase2");
        sDevUtilsIgnoreCatalogs.add("DevBaseMVVM");
        sDevUtilsIgnoreCatalogs.add("DevEngine");
        sDevUtilsIgnoreCatalogs.add("DevHttpCapture");
        sDevUtilsIgnoreCatalogs.add("DevHttpManager");
        sDevUtilsIgnoreCatalogs.add("DevRetrofit");
        sDevUtilsIgnoreCatalogs.add("DevJava");
        sDevUtilsIgnoreCatalogs.add("DevOther");
        sDevUtilsIgnoreCatalogs.add("DevSKU");
        sDevUtilsIgnoreCatalogs.add("DevWidget");
        sDevUtilsIgnoreCatalogs.add("DevEnvironment");
        sDevUtilsIgnoreCatalogs.add("DevEnvironmentBase");
        sDevUtilsIgnoreCatalogs.add("DevEnvironmentCompiler");
        sDevUtilsIgnoreCatalogs.add("DevEnvironmentCompilerRelease");
        sDevUtilsIgnoreCatalogs.add("DevHttpCaptureCompiler");
        sDevUtilsIgnoreCatalogs.add("DevHttpCaptureCompilerRelease");

        // ========================
        // = DevUtils Application =
        // ========================

        sDevUtilsApplicationCatalogMap.put("application", "根目录");
        sDevUtilsApplicationCatalogMap.put(".DevBaseDemo", "临时测试代码、库调用调试 Demo");
        sDevUtilsApplicationCatalogMap.put(".DevUtilsApp", "DevUtils 代码演示应用");

        // =================
        // = DevUtils-repo =
        // =================

        // =============================
        // = DevUtils-repo Application =
        // =============================

        sDevUtilsRepoApplicationCatalogMap.put("application", "根目录");
        sDevUtilsRepoApplicationCatalogMap.put(".Accessibility", "Android 无障碍使用 ( Activity 栈 )");
        sDevUtilsRepoApplicationCatalogMap.put(".AppInfo", "APP 信息提取器");
        sDevUtilsRepoApplicationCatalogMap.put(".GTPush", "个推推送 ( 逻辑 ) 处理 ( 含设备管理 )");
        sDevUtilsRepoApplicationCatalogMap.put(".JPush", "极光推送 ( 逻辑 ) 处理 ( 含设备管理 )");
        sDevUtilsRepoApplicationCatalogMap.put(".UMShare", "友盟分享 ( 逻辑 ) 处理");

        // ========================
        // = DevUtils-repo Module =
        // ========================

        sDevUtilsRepoModuleCatalogMap.put("module", "根目录");
        sDevUtilsRepoModuleCatalogMap.put(".DevBaseModule", "Module 基础复用组件");
        sDevUtilsRepoModuleCatalogMap.put(".push", "推送 SDK Engine 实现 ( 推送组件化实现 )");
        sDevUtilsRepoModuleCatalogMap.put(".push.DevGTPush", "个推推送 Engine 实现代码");
        sDevUtilsRepoModuleCatalogMap.put(".push.DevJPush", "极光推送 Engine 实现代码");
        sDevUtilsRepoModuleCatalogMap.put(".share", "分享 SDK Engine 实现 ( 分享组件化实现 )");
        sDevUtilsRepoModuleCatalogMap.put(".share.DevUMShare", "友盟分享 Engine 实现代码");

        // ================================
        // = DevUtils-repo Module 忽略目录 =
        // ================================

        sDevUtilsRepoModuleIgnoreCatalogs.add("DevBaseModule");

        // =============================
        // = DevUtils-repo Interesting =
        // =============================

        sDevUtilsRepoInterestingCatalogMap.put("interesting", "根目录");
        sDevUtilsRepoInterestingCatalogMap.put(".DevScreenMatch", "Android 屏幕适配生成对应的尺寸文件");
        sDevUtilsRepoInterestingCatalogMap.put(".DevStandard", "项目规范统一检测、生成替换等");
    }
}