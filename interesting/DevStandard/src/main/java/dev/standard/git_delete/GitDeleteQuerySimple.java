package dev.standard.git_delete;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * detail: Git 文件删除搜索、生成 CMD
 * @author Ttt
 */
public class GitDeleteQuerySimple {

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 统一打印方法
     * @param object 打印内容
     */
    private static void println(final Object object) {
        System.out.println(object);
    }

    // 项目 git
    private static final String       PROJECT_GIT     = "DevComponent.git";
    // 项目 git 链接
    private static final String       PROJECT_GIT_URL = "https://github.com/afkT" + File.separator + PROJECT_GIT;
    // 用户名
    private static final String       USER            = "afkT"; // macOC /Users/${USER}
    // 待删除文件夹
    private static final List<String> FOLDER_LIST     = new ArrayList<>();
    // 待删除文件
    private static final List<String> FILE_LIST       = new ArrayList<>();

    static {
        FOLDER_LIST.add("art");
        FOLDER_LIST.add("link");
        FOLDER_LIST.add("project");
        FOLDER_LIST.add("lib");
        FOLDER_LIST.add("libs");
        FOLDER_LIST.add("interesting");
        FOLDER_LIST.add("app");
        FOLDER_LIST.add("application");
        FOLDER_LIST.add("file");
        FOLDER_LIST.add("core");
        FOLDER_LIST.add("component");
        FOLDER_LIST.add("module");

        FILE_LIST.add("android_standard.md");
        FILE_LIST.add("java_standard.md");
        FILE_LIST.add("git_standard.md");
        FILE_LIST.add("icon_launcher.png");
        FILE_LIST.add("icon_launcher_round.png");
        FILE_LIST.add("icon_launcher_round2.png");
        FILE_LIST.add("core_ui_module_logo.png");
    }

    /**
     * 生成 CMD 命令
     */
    private static void generateCMD() {
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

        // 删除文件夹
        println("");
        println("============");
        println("= 删除文件夹 =");
        println("============");
        println("");
        for (String value : FOLDER_LIST) {
            cmd = "%s --delete-folders %s %s";
            println(String.format(cmd, JAVA_BFG, value, USER_GIT));
        }

        // 删除文件
        println("");
        println("===========");
        println("= 删除文件 =");
        println("===========");
        println("");
        for (String value : FILE_LIST) {
            cmd = "%s --delete-files %s %s";
            println(String.format(cmd, JAVA_BFG, value, USER_GIT));
        }

        println("");
        println("");
    }

    // ========
    // = Main =
    // ========

    public static void main(String[] args) {
        // 生成 CMD 命令
        generateCMD();

        String debug = "";
    }
}