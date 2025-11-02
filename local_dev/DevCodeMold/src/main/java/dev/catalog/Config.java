package dev.catalog;

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
    public static final String              DEV_UTILS_REPO_APPLICATION_LOCAL_PATH = USER_DIR + File.separator + DEV_UTILS_REPO_APPLICATION_DIR_NAME;
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

    // ===========================
    // = DevUtils-repo Local Dev =
    // ===========================

    // DevUtils-repo Local Dev 文件名
    public static final String              DEV_UTILS_REPO_LOCAL_DEV_DIR_NAME   = "local_dev";
    // DevUtils-repo Local Dev 项目本地路径
    public static final String              DEV_UTILS_REPO_LOCAL_DEV_LOCAL_PATH = USER_DIR + File.separator + DEV_UTILS_REPO_LOCAL_DEV_DIR_NAME;
    // DevUtils-repo Local Dev 文件目录注释
    public static final Map<String, String> sDevUtilsRepoLocalDevCatalogMap     = new HashMap<>();

    static {

        // ============
        // = DevUtils =
        // ============

        // ================
        // = DevUtils Lib =
        // ================

        sDevUtilsCatalogMap.put("lib", "根目录");
        sDevUtilsCatalogMap.put(".DevApp", "Android 工具类库");
        sDevUtilsCatalogMap.put(".DevAssist", "封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等");
        sDevUtilsCatalogMap.put(".DevBase", "DevBase - Base ( Activity、Fragment ) MVP、MVVM 基类库");
        sDevUtilsCatalogMap.put(".DevEngine", "第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用");
        sDevUtilsCatalogMap.put(".DevSimple", "简单敏捷开发库");
        sDevUtilsCatalogMap.put(".DevWidget", "自定义 View UI 库");
        sDevUtilsCatalogMap.put(".DevDeprecated", "Dev 系列库弃用代码统一存储库");
        sDevUtilsCatalogMap.put(".Environment", "Android 环境配置切换库");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironment", "环境切换可视化 UI 操作");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironmentBase", "注解类、实体类、监听事件等通用基础");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironmentCompiler", "Debug ( 打包 / 编译 ) 生成实现代码");
        sDevUtilsCatalogMap.put(".Environment.DevEnvironmentCompilerRelease", "Release ( 打包 / 编译 ) 生成实现代码");
        sDevUtilsCatalogMap.put(".HttpCapture", "Android 抓包库");
        sDevUtilsCatalogMap.put(".HttpCapture.DevHttpCapture", "OkHttp 抓包工具库");
        sDevUtilsCatalogMap.put(".HttpCapture.DevHttpCaptureCompiler", "Debug ( 打包 / 编译 ) 实现代码 ( 可视化 UI 操作 )");
        sDevUtilsCatalogMap.put(".HttpCapture.DevHttpCaptureCompilerRelease", "Release ( 打包 / 编译 ) 实现代码");
        sDevUtilsCatalogMap.put(".HttpRequest", "Android 网络请求库");
        sDevUtilsCatalogMap.put(".HttpRequest.DevRetrofit", "Retrofit + Kotlin Coroutines 封装");
        sDevUtilsCatalogMap.put(".HttpRequest.DevHttpManager", "OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )");
        sDevUtilsCatalogMap.put(".DevJava", "Java 工具类库 ( 不依赖 android api )");

        // =======================
        // = DevUtils Lib 忽略目录 =
        // =======================

        sDevUtilsIgnoreCatalogs.add("DevApp");
        sDevUtilsIgnoreCatalogs.add("DevAssist");
        sDevUtilsIgnoreCatalogs.add("DevBase");
        sDevUtilsIgnoreCatalogs.add("DevEngine");
        sDevUtilsIgnoreCatalogs.add("DevSimple");
        sDevUtilsIgnoreCatalogs.add("DevWidget");
        sDevUtilsIgnoreCatalogs.add("DevDeprecated");
        sDevUtilsIgnoreCatalogs.add("DevEnvironment");
        sDevUtilsIgnoreCatalogs.add("DevEnvironmentBase");
        sDevUtilsIgnoreCatalogs.add("DevEnvironmentCompiler");
        sDevUtilsIgnoreCatalogs.add("DevEnvironmentCompilerRelease");
        sDevUtilsIgnoreCatalogs.add("DevHttpCapture");
        sDevUtilsIgnoreCatalogs.add("DevHttpCaptureCompiler");
        sDevUtilsIgnoreCatalogs.add("DevHttpCaptureCompilerRelease");
        sDevUtilsIgnoreCatalogs.add("DevRetrofit");
        sDevUtilsIgnoreCatalogs.add("DevHttpManager");
        sDevUtilsIgnoreCatalogs.add("DevJava");

        // ========================
        // = DevUtils Application =
        // ========================

        sDevUtilsApplicationCatalogMap.put("application", "根目录");
        sDevUtilsApplicationCatalogMap.put(".DevUtilsApp", "DevUtils 代码演示应用");
        sDevUtilsApplicationCatalogMap.put(".DevEnvironmentUse", "DevEnvironment - Android 环境配置切换库【使用示例】");
        sDevUtilsApplicationCatalogMap.put(".DevHttpCaptureUse", "DevHttpCapture - OkHttp 抓包工具库【使用示例】");
        sDevUtilsApplicationCatalogMap.put(".DevHttpManagerUse", "DevHttpManager - OkHttp 管理库【使用示例】");
        sDevUtilsApplicationCatalogMap.put(".DevRetrofitUse", "DevRetrofit - Retrofit + Kotlin Coroutines 封装【使用示例】");

        // =================
        // = DevUtils-repo =
        // =================

        // =============================
        // = DevUtils-repo Application =
        // =============================

        sDevUtilsRepoApplicationCatalogMap.put("application", "根目录");
        sDevUtilsRepoApplicationCatalogMap.put(".Accessibility", "Android 无障碍使用 ( Activity 栈 )");
        sDevUtilsRepoApplicationCatalogMap.put(".AppDB", "Room、GreenDAO Database 使用对比");
        sDevUtilsRepoApplicationCatalogMap.put(".GTPush", "个推推送 ( 逻辑 ) 处理");
        sDevUtilsRepoApplicationCatalogMap.put(".JPush", "极光推送 ( 逻辑 ) 处理");
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

        // ===========================
        // = DevUtils-repo Local Dev =
        // ===========================

        sDevUtilsRepoLocalDevCatalogMap.put("local_dev", "根目录");
        sDevUtilsRepoLocalDevCatalogMap.put(".DevCodeMold", "代码生成模具【屏幕适配、IntentData 创建、dimens.xml 生成】等");
        sDevUtilsRepoLocalDevCatalogMap.put(".DevOther", "功能、工具类二次封装, 直接 copy 使用");
    }
}