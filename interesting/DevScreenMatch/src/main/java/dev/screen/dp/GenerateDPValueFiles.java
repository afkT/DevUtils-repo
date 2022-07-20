package dev.screen.dp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import dev.screen.dp.utils.DimenItem;
import dev.screen.dp.utils.Tools;
import dev.screen.dp.utils.XmlIO;

/**
 * detail: 生成适配 dp 值
 * @author Ttt
 * @author duke
 * <pre>
 *     基准文件, 在本项目目录下 /res/values/dimens.xml
 *     把想要适配的文件替换 dimens.xml, 并且修改基准 dp 值 BASE_DP => 以常用开发测试手机的 dp 为基准
 * </pre>
 */
final class GenerateDPValueFiles {

    public static void main(String[] args) {
        new GenerateDPValueFiles().generate(true, DIR, true);
    }

    // 项目路径
    private static final String PROJECT_PATH    = "/interesting/DevScreenMatch/res";
    // 存储地址
    private static final String DIR             = new File(System.getProperty("user.dir"), PROJECT_PATH).getAbsolutePath();
    // 适配基准文件
    private static final String ADAPTATION_FILE = "/values/dimens.xml";

    // 基准 dp 如: 360dp
    private final        double            BASE_DP               = 360;
    // 生成的 values 目录格式 ( 代码中替换 XXX 字符串 )
    private final        String            LETTER_REPLACE        = "XXX";
    // values-w410dp 这个目录需要删除
    private final        String            VALUES_OLD_FOLDER     = "values-wXXXdp";
    // values-sw410dp
    private final        String            VALUES_NEW_FOLDER     = "values-swXXXdp";
    // 是否删除旧的目录格式
    private final        boolean           DELETE_ANOTHER_FOLDER = true;
    // 去重复的数据集合
    private final        HashSet<Double>   DATA_SET              = new HashSet<>();
    // 对应的 dp 值
    private static final ArrayList<String> listDPs               = new ArrayList<>();

    static {
        listDPs.add("384");
        listDPs.add("392");
        listDPs.add("400");
        listDPs.add("410");
        listDPs.add("411");
        listDPs.add("480");
        listDPs.add("533");
        listDPs.add("592");
        listDPs.add("600");
        listDPs.add("640");
        listDPs.add("662");
        listDPs.add("720");
        listDPs.add("768");
        listDPs.add("800");
        listDPs.add("811");
        listDPs.add("820");
        listDPs.add("960");
        listDPs.add("961");
        listDPs.add("1024");
        listDPs.add("1280");
        listDPs.add("1365");
    }

    /**
     * 生成适配文件
     * @param isFontMatch    字体是否也适配 ( 是否与 dp 尺寸一样等比缩放 )
     * @param resFolderPath  base dimens.xml 文件的 res 目录
     * @param isUseNewFolder 是否创建 values-swXXXdp 新格式的目录
     */
    public void generate(
            boolean isFontMatch,
            String resFolderPath,
            boolean isUseNewFolder
    ) {
        // 添加默认的数据
        for (String dbValue : listDPs) {
            if (dbValue == null || "".equals(dbValue.trim())) {
                continue;
            }
            try {
                DATA_SET.add(Double.parseDouble(dbValue.trim()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        System.out.println("基准宽度 dp 值: [ " + Tools.cutLastZero(BASE_DP) + " dp ]");
        System.out.println("本次待适配的宽度 dp 值: [ " + Tools.getOrderedString(DATA_SET) + " ]");
        // 获取适配基准 dimens.xml 文件
        String baseDimenFilePath = resFolderPath + ADAPTATION_FILE;
        File   testBaseDimenFile = new File(baseDimenFilePath);
        // 判断基准文件是否存在
        if (!testBaseDimenFile.exists()) {
            System.out.println(String.format("DK WARNING: %s 路径下的文件找不到!", ADAPTATION_FILE));
            return;
        }
        // 解析源 dimens.xml 文件
        List<DimenItem> list = XmlIO.readDimenFile(baseDimenFilePath);
        if (list == null || list.size() <= 0) {
            System.out.println("DK WARNING: dimens.xml 文件无数据!");
            return;
        } else {
            System.out.println("OK, 基准 dimens.xml 文件解析成功!");
        }
        try {
            // 循环指定的 dp 参数, 生成对应的 dimens-swXXXdp.xml 文件
            Iterator<Double> iterator = DATA_SET.iterator();
            while (iterator.hasNext()) {
                double item = iterator.next();
                // 获取当前 dp 除以 BASE_DP 后的倍数
                double multiple = item / BASE_DP;
                // 待输出的目录
                String outFolderPath;
                // 待删除的目录
                String delFolderPath;
                // values 目录上带的 dp 整数值
                String folderDP = String.valueOf((int) item);

                if (isUseNewFolder) {
                    outFolderPath = VALUES_NEW_FOLDER.replace(LETTER_REPLACE, folderDP);
                    delFolderPath = VALUES_OLD_FOLDER.replace(LETTER_REPLACE, folderDP);
                } else {
                    outFolderPath = VALUES_OLD_FOLDER.replace(LETTER_REPLACE, folderDP);
                    delFolderPath = VALUES_NEW_FOLDER.replace(LETTER_REPLACE, folderDP);
                }
                outFolderPath = resFolderPath + File.separator + outFolderPath + File.separator;
                delFolderPath = resFolderPath + File.separator + delFolderPath + File.separator;

                if (DELETE_ANOTHER_FOLDER) {
                    // 删除以前适配方式的目录 values-wXXXdp
                    File oldFile = new File(delFolderPath);
                    if (oldFile.exists() && oldFile.isDirectory() && Tools.isOldFolder(oldFile.getName(), isUseNewFolder)) {
                        // 找出 res 目录下符合要求的 values 目录, 然后删除 values 目录
                        Tools.deleteAllInDir(oldFile);
                    }
                }

                // 生成新的目录 values-swXXXdp
                new File(outFolderPath).mkdirs(); // 创建当前 dp 对应的 dimens 文件目录
                // 生成的 dimens 文件的路径
                String outPutFile = outFolderPath + "dimens.xml";
                // 生成目标文件 dimens.xml 输出目录
                boolean result = XmlIO.createDestinationDimens(isFontMatch, list, multiple, outPutFile);
                if (result) {
                    System.out.println(String.format("OK, %s 文件生成成功!", outPutFile));
                } else {
                    System.out.println(String.format("DK WARNING: %s 文件生成失败!", outPutFile));
                }
            }
            System.out.println("OK ALL OVER, 全部生成完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}