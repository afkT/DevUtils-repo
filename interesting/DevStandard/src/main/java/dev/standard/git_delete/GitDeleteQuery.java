package dev.standard.git_delete;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.utils.common.BigDecimalUtils;
import dev.utils.common.FileUtils;

/**
 * detail: Git 文件删除搜索、生成 CMD
 * @author Ttt
 */
public class GitDeleteQuery {

    // ==========
    // = 内部方法 =
    // ==========

    // application 文件夹
    private static final String       FOLDER_APPLICATION   = "application";
    // art 文件夹
    private static final String       FOLDER_ART           = "art";
    // interesting 文件夹
    private static final String       FOLDER_INTERESTING   = "interesting";
    // module 文件夹
    private static final String       FOLDER_MODULE        = "module";
    // 忽略文件夹
    private static final List<String> IGNORE_FOLDER_LIST   = new ArrayList<>();
    // 忽略文件
    private static final List<String> IGNORE_FILE_LIST     = new ArrayList<>();
    // 忽略文件夹、文件记录
    private static final List<String> IGNORE_FOLDER_RECORD = new ArrayList<>();
    private static final List<String> IGNORE_FILE_RECORD   = new ArrayList<>();

    static {
        IGNORE_FOLDER_LIST.add("DevBaseDemo");
        IGNORE_FOLDER_LIST.add("DevUtilsApp");

        IGNORE_FILE_LIST.add("module.png");
        IGNORE_FILE_LIST.add("README.md");
        IGNORE_FILE_LIST.add(".DS_Store");
    }

    /**
     * 搜索指定目录全部文件
     * @param path        文件路径
     * @param isRecursive 是否递归进子目录
     * @return 指定目录全部文件集合
     */
    private static List<File> searchAllFile(
            final String path,
            final boolean isRecursive
    ) {
        List<FileUtils.FileList> files = FileUtils.listFilesInDirWithFilterBean(path, new FileFilter() {
            @Override
            public boolean accept(File file) {
                // 忽略隐藏文件夹
                if (file.isDirectory() && file.isHidden()) {
                    return false;
                }
                // 属于文件夹
                if (file.isDirectory()) {
                    String name = file.getName();
                    for (String value : IGNORE_FOLDER_LIST) {
                        // 判断是否需要忽略的文件夹
                        if (value.equals(name)) {
                            String path = file.getAbsolutePath();
                            if (!IGNORE_FOLDER_RECORD.contains(path)) {
                                IGNORE_FOLDER_RECORD.add(path);
                            }
                            return false;
                        }
                    }
                } else if (file.isFile()) {
                    String name = file.getName();
                    for (String value : IGNORE_FILE_LIST) {
                        // 判断是否需要忽略的文件
                        if (value.equals(name)) {
                            String path = file.getAbsolutePath();
                            if (!IGNORE_FILE_RECORD.contains(path)) {
                                IGNORE_FILE_RECORD.add(path);
                            }
                            return false;
                        }
                    }
                }
                return true;
            }
        }, isRecursive);
        List<File> list = new ArrayList<>();
        for (FileUtils.FileList bean : files) {
            forFileListBean(list, bean);
        }
        return list;
    }

    /**
     * 循环追加文件
     * @param list 数据源
     * @param bean 文件信息
     */
    private static void forFileListBean(
            final List<File> list,
            final FileUtils.FileList bean
    ) {
        list.add(bean.getFile());
        if (bean.getSubFiles().isEmpty()) {
            return;
        }
        for (FileUtils.FileList value : bean.getSubFiles()) {
            forFileListBean(list, value);
        }
    }

    /**
     * 获取待删除文件夹路径
     * @return 待删除文件夹路径
     */
    private static Map<String, String> getWaitDeletePath() {
        List<String> keys = Arrays.asList(
                FOLDER_APPLICATION, FOLDER_ART,
                FOLDER_INTERESTING, FOLDER_MODULE
        );
        Map<String, String> paths = new LinkedHashMap<>();
        for (String key : keys) {
            paths.put(key, PATH_FOLDER + File.separator + key);
        }
        return paths;
    }

    /**
     * 获取待删除文件列表
     * @param isRecursive 是否递归进子目录
     * @return 待删除文件列表
     */
    private static Map<String, List<File>> getWaitDeleteMap(final boolean isRecursive) {
        Map<String, List<File>> map   = new LinkedHashMap<>();
        Map<String, String>     paths = getWaitDeletePath();
        for (String key : paths.keySet()) {
            map.put(key, searchAllFile(paths.get(key), isRecursive));
        }
        return map;
    }

    /**
     * 转换待删除文件名集合
     * @param map 待删除文件集合
     */
    private static List<String> convertDeleteNames(final Map<String, List<File>> map) {
        List<String> names = new ArrayList<>();
        for (String key : map.keySet()) {
            if (!names.contains(key)) {
                names.add(key);
            }
            for (File file : map.get(key)) {
                String name = file.getName();
                if (!names.contains(name)) {
                    names.add(name);
                }
            }
        }
        return names;
    }

    /**
     * 检测删除文件重复信息
     * @param files 待校验文件列表
     * @param names 待删除文件名集合
     * @return 重复的文件信息
     */
    private static List<File> checkDeleteRepeatFile(
            final List<File> files,
            final List<String> names
    ) {
        Collection<String> paths  = getWaitDeletePath().values();
        List<File>         repeat = new ArrayList<>();
        for (File file : files) {
            String name = file.getName();
            if (names.contains(name)) {
                boolean add  = true;
                String  path = file.getPath();
                for (String key : paths) {
                    if (path.startsWith(key)) {
                        add = false;
                    }
                }
                if (add) {
                    repeat.add(file);
                }
            }
        }
        return repeat;
    }

    /**
     * 计算文件集合大小
     * @param list 文件集合
     * @return 文件集合大小
     */
    private static double calculateSize(final List<File> list) {
        double size = 0;
        for (File file : list) {
            // 属于文件才计算
            if (file.isFile()) {
                size += FileUtils.getFileLength(file);
            }
        }
        return size;
    }

    /**
     * 计算待删除文件集合大小
     * @param map 待计算文件集合
     * @return 待删除文件集合大小
     */
    private static Map<String, Double> calculateMapSize(final Map<String, List<File>> map) {
        Map<String, Double> sizeMap = new LinkedHashMap<>();
        for (String key : map.keySet()) {
            sizeMap.put(key, calculateSize(map.get(key)));
        }
        return sizeMap;
    }

    /**
     * 打印待删除文件集合大小
     * @param map 待删除文件集合大小
     */
    private static void printMapSize(final Map<String, List<File>> map) {
        Map<String, Double> sizeMap = calculateMapSize(map);

        double allSize = 0.0D;
        println("");
        println("======================");
        println("= 待删除文件大小计算打印 =");
        println("======================");
        println("");
        for (String key : sizeMap.keySet()) {
            Double value    = sizeMap.get(key);
            String valueStr = BigDecimalUtils.operation(value).toPlainString();
            println(key + " 文件夹大小: " + valueStr + " => " + FileUtils.formatFileSize(value));
            allSize += value;
        }
        println("");
        String allSizeStr = BigDecimalUtils.operation(allSize).toPlainString();
        println("总大小: " + allSizeStr + " => " + FileUtils.formatFileSize(allSize));
    }

    /**
     * 打印重复文件资源
     * @param repeatList 重复文件资源
     */
    private static void printRepeatList(final List<File> repeatList) {
        println("");
        println("=================");
        println("= 重复文件信息打印 =");
        println("=================");
        println("");
        for (File file : repeatList) {
            println(file.getAbsolutePath());
        }
    }

    /**
     * 统一打印方法
     * @param object 打印内容
     */
    private static void println(final Object object) {
        System.out.println(object);
    }

    // =

    // 项目文件夹路径 ( 旧项目文件目录读取 )
    private static final String PATH_FOLDER     = "/Users/afkT/Documents/GitHub/DevUtils-backup";
    // 项目 git
    private static final String PROJECT_GIT     = "DevUtils.git";
    // 项目 git 链接
    private static final String PROJECT_GIT_URL = "https://github.com/afkT" + File.separator + PROJECT_GIT;
    // 用户名
    private static final String USER            = "afkT"; // macOC /Users/${USER}

    /**
     * 生成 CMD 命令
     * @param mapFile 待删除文件列表
     */
    private static void generateCMD(final Map<String, List<File>> mapFile) {
        final String JAVA_BFG = String.format("java -jar /Users/%s/bfg.jar", USER);
        final String USER_GIT = String.format("/Users/%s/%s", USER, PROJECT_GIT);

        // 获取 git 大小 ( 需要进入待计算项目目录中 )
        println("");
        println("===============");
        println("= 获取 git 大小 =");
        println("===============");
        println("");
        println("git count-objects -vH");

        // ==========
        // = 开始顺序 =
        // ==========

        // 克隆项目
        println("");
        println("===========");
        println("= 克隆项目 =");
        println("===========");
        println("");
        println("git clone --mirror " + PROJECT_GIT_URL);

        // 移除 1 mb 以上文件大小
        println("");
        println("=======================");
        println("= 移除 1 mb 以上文件大小 =");
        println("=======================");
        println("");
        String cmd = "%s --strip-blobs-bigger-than 1M %s";
        println(String.format(cmd, JAVA_BFG, USER_GIT));

        // 进入项目 git 文件夹
        println("");
        println("====================");
        println("= 进入项目 git 文件夹 =");
        println("====================");
        println("");
        println(String.format("cd %s", PROJECT_GIT));

        // 进行回收
        println("");
        println("============");
        println("= 回收命令行 =");
        println("============");
        println("");
        println("git reflog expire --expire=now --all && git gc --prune=now --aggressive");

        // 强制推送
        println("");
        println("==========");
        println("= 强制推送 =");
        println("==========");
        println("");
        println("git push --force");

        // =

        println("");
        println("========================");
        println("= 其他删除文件、文件夹命令 =");
        println("========================");

        // 删除 interesting 文件夹
        println("");
        println("=========================");
        println("= 删除 interesting 文件夹 =");
        println("=========================");
        println("");
        cmd = "%s --delete-folders %s %s";
        println(String.format(cmd, JAVA_BFG, FOLDER_INTERESTING, USER_GIT));

        // 删除 module 文件夹
        println("");
        println("====================");
        println("= 删除 module 文件夹 =");
        println("====================");
        println("");
        cmd = "%s --delete-folders %s %s";
        println(String.format(cmd, JAVA_BFG, FOLDER_MODULE, USER_GIT));

        // 删除 application 文件夹下的内容
        println("");
        println("================================");
        println("= 删除 application 文件夹下的内容 =");
        println("================================");
        println("");
        List<File> list = mapFile.get(FOLDER_APPLICATION);
        for (File file : list) {
            cmd = "%s --delete-folders %s %s";
            println(String.format(cmd, JAVA_BFG, file.getName(), USER_GIT));
        }

        // 删除 art 文件夹下的内容
        println("");
        println("========================");
        println("= 删除 art 文件夹下的内容 =");
        println("========================");
        println("");
        list = mapFile.get(FOLDER_ART);
        for (File file : list) {
            cmd = "%s --delete-files %s %s";
            println(String.format(cmd, JAVA_BFG, file.getName(), USER_GIT));
        }

        println("");
        println("");
    }

    // ========
    // = Main =
    // ========

    public static void main(String[] args) {
        // 搜索指定目录全部文件
        List<File> files = searchAllFile(PATH_FOLDER, true);
        // 获取待删除文件列表 ( 递归全部子文件夹 )
        Map<String, List<File>> maps = getWaitDeleteMap(true);
        printMapSize(maps);

        // 获取待删除文件列表
        Map<String, List<File>> mapFile = getWaitDeleteMap(false);
        // 保存待删除文件夹名、文件名
        List<String> names = convertDeleteNames(mapFile);
        // 检测删除文件重复信息
        List<File> repeatList = checkDeleteRepeatFile(files, names);
        printRepeatList(repeatList);

        // 生成 CMD 命令
        generateCMD(mapFile);

        String debug = "";
    }
}