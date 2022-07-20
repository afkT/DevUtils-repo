package dev.standard.dev_able;

import java.io.File;
import java.util.List;

import dev.utils.DevFinal;
import dev.utils.common.FileIOUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: DevFinal 字符串常量排序工具类
 * @author Ttt
 */
final class Utils {

    private Utils() {
    }

    // ============
    // = 统一实体类 =
    // ============

    public static class Replace {

        public static final String AAAAA = "AAAAA";
        public static final String BBBBB = "BBBBB";
        public static final String CCCCC = "CCCCC";

        // 功能名 -> 替换 AAAAA
        public final String name;

        // 方法名 -> 替换 BBBBB
        public final String methodName;

        // 补充注释 -> 替换 CCCCC
        public final String annotation;

        public Replace(
                final String name,
                final String methodName
        ) {
            this(name, methodName, "");
        }

        public Replace(
                final String name,
                final String methodName,
                final String annotation
        ) {
            this.name       = name;
            this.methodName = methodName;
            this.annotation = annotation;
        }

        public String getFileName() {
            return name + "able.java";
        }
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 生成 able 文件
     * @param list 待生成信息
     */
    public static void generateAbleFile(final List<Replace> list) {
        for (Replace info : list) {
            String formatTxt = getFormatTXT();
            String content = formatTxt.replaceAll(
                    Replace.AAAAA, info.name
            ).replaceAll(
                    Replace.BBBBB, info.methodName
            ).replaceAll(
                    Replace.CCCCC, info.annotation
            );
            File devAppAble = FileUtils.getFile(
                    getGenerateDirectory("DevApp"),
                    info.getFileName()
            );
            File devJavaAble = FileUtils.getFile(
                    getGenerateDirectory("DevJava"),
                    info.getFileName()
            );
            // 保存文件
            saveFile(content, FileUtils.getAbsolutePath(devAppAble));
            saveFile(content, FileUtils.getAbsolutePath(devJavaAble));
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    private static String FORMAT_TXT = null;

    /**
     * 获取 format.txt 文本内容
     * @return format.txt 文本内容
     */
    private static String getFormatTXT() {
        if (FORMAT_TXT == null) {
            FORMAT_TXT = FileIOUtils.readFileToString(getFormatFilePath());
        }
        return FORMAT_TXT;
    }

    // ==========
    // = 保存结果 =
    // ==========

    /**
     * 保存文件
     * @param content  保存内容
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    private static boolean saveFile(
            final String content,
            final String filePath
    ) {
        // 移除最后一行换行符
        String finalContent = StringUtils.clearEndsWith(
                content, DevFinal.SYMBOL.NEW_LINE
        );
        boolean result = FileUtils.saveFile(
                filePath, finalContent.getBytes()
        );
        System.out.println("保存结果: " + result);
        return result;
    }

    // ==========
    // = 路径相关 =
    // ==========

    private static String getPackagePath() {
        return new File(
                System.getProperty("user.dir"),
                "/interesting/DevStandard/src/main/java/dev/standard/dev_able"
        ).getAbsolutePath();
    }

    private static String getFormatFilePath() {
        return new File(getPackagePath(), "format.txt").getAbsolutePath();
    }

    private static String getGenerateDirectory(final String module) {
        return new File(
                System.getProperty("user.dir"),
                "/lib/" + module + "/src/main/java/dev/utils/common/able"
        ).getAbsolutePath();
    }
}