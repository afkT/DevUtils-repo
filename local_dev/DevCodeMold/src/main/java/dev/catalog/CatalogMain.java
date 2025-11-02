package dev.catalog;

import java.util.List;
import java.util.Map;

import dev.utils.DevFinal;
import dev.utils.common.FileUtils;

/**
 * detail: 目录生成 Main 方法
 * @author Ttt
 */
final class CatalogMain {

    private static StringBuilder sBuilder = new StringBuilder();

    public static void main(String[] args) {

        // =================
        // = afkT/DevUtils =
        // =================

        // 生成 DevUtils Lib 汇总项目目录结构 - https://github.com/afkT/DevUtils/blob/master/lib
        print(
                Config.DEV_UTILS_LOCAL_PATH, Config.DEV_UTILS_DIR_NAME,
                Config.sDevUtilsCatalogMap, Config.sDevUtilsIgnoreCatalogs, 2
        );

        // 生成 DevUtils Application 汇总项目目录结构 - https://github.com/afkT/DevUtils/blob/master/application
        print(
                Config.DEV_UTILS_APPLICATION_LOCAL_PATH, Config.DEV_UTILS_APPLICATION_DIR_NAME,
                Config.sDevUtilsApplicationCatalogMap, null, 0
        );

        // ======================
        // = afkT/DevUtils-repo =
        // ======================

        // 生成 DevUtils-repo Application 汇总项目目录结构 - https://github.com/afkT/DevUtils-repo/blob/main/application
        print(
                Config.DEV_UTILS_REPO_APPLICATION_LOCAL_PATH, Config.DEV_UTILS_REPO_APPLICATION_DIR_NAME,
                Config.sDevUtilsRepoApplicationCatalogMap, null, 0
        );

        // 生成 DevUtils-repo Module 汇总项目目录结构 - https://github.com/afkT/DevUtils-repo/blob/main/module
        print(
                Config.DEV_UTILS_REPO_MODULE_LOCAL_PATH, Config.DEV_UTILS_REPO_MODULE_DIR_NAME,
                Config.sDevUtilsRepoModuleCatalogMap, Config.sDevUtilsRepoModuleIgnoreCatalogs, 1
        );

        // 生成 DevUtils-repo Local Dev 汇总项目目录结构 - https://github.com/afkT/DevUtils-repo/blob/main/local_dev
        print(
                Config.DEV_UTILS_REPO_LOCAL_DEV_LOCAL_PATH, Config.DEV_UTILS_REPO_LOCAL_DEV_DIR_NAME,
                Config.sDevUtilsRepoLocalDevCatalogMap, null, 0
        );

        // =========
        // = debug =
        // =========

        String all = sBuilder.toString();
    }

    private static final String FORMAT = "\"%s\" not found";

    /**
     * 打印目录信息
     * @param path              文件路径
     * @param dirName           文件名
     * @param mapCatalog        对应目录注释
     * @param listIgnoreCatalog 忽略目录
     * @param layer             目录层级
     */
    private static void print(
            final String path,
            final String dirName,
            final Map<String, String> mapCatalog,
            final List<String> listIgnoreCatalog,
            final int layer
    ) {
        System.out.println(DevFinal.SYMBOL.NEW_LINE_X2);
        if (FileUtils.isFileExists(path)) {
            String catalog = CatalogGenerate.generate(
                    path, dirName, mapCatalog, listIgnoreCatalog, layer
            );
            System.out.println(catalog);
            // 拼接全部
            sBuilder.append(catalog)
                    .append(DevFinal.SYMBOL.NEW_LINE_X2);
        } else {
            System.out.println(String.format(FORMAT, path));
        }
    }
}