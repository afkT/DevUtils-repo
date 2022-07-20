package dev.screen.px;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * detail: 生成适配 px 值
 * @author Ttt
 * <pre>
 *     只需要修改以下两个值
 *     BASE_WIDTH => UI 设计对应的屏幕宽度
 *     BASE_HEIGHT => UI 设计对应的屏幕高度
 * </pre>
 */
final class GeneratePxValueFiles {

    public static void main(String[] args) {
        new GeneratePxValueFiles().generate();
    }

    // 项目路径
    private final String PROJECT_PATH = "/interesting/DevScreenMatch/res/";
    // 存储地址
    private final String DIR          = new File(System.getProperty("user.dir"), PROJECT_PATH).getAbsolutePath();

    // 基准宽度
    private final        int               BASE_WIDTH      = 750;
    // 基准高度
    private final        int               BASE_HEIGHT     = 1334;
    // values-480x320
    private final        String            VALUE_TEMPLATE  = "values-{0}x{1}";
    // <dimen name="x1">1px</dimen>
    private final        String            WIDTH_TEMPLATE  = "\t<dimen name=\"x{0}\">{1}px</dimen>\n";
    // <dimen name="y1">1px</dimen>
    private final        String            HEIGHT_TEMPLATE = "\t<dimen name=\"y{0}\">{1}px</dimen>\n";
    // 保存需要生成处理的尺寸
    private static final ArrayList<String> listDimesions   = new ArrayList<>();

    static {
        // width, height
        listDimesions.add("320,480");
        listDimesions.add("480,800");
        listDimesions.add("480,845");
        listDimesions.add("480,854");
        listDimesions.add("540,888");
        listDimesions.add("540,960");
        listDimesions.add("600,1024");
        listDimesions.add("640,1136");
        listDimesions.add("720,1184");
        listDimesions.add("720,1196");
        listDimesions.add("720,1280");
        listDimesions.add("750,1334");
        listDimesions.add("768,1024");
        listDimesions.add("768,1280");
        listDimesions.add("800,1280");
        listDimesions.add("1080,1776");
        listDimesions.add("1080,1812");
        listDimesions.add("1080,1920");
        listDimesions.add("1080,2400");
        listDimesions.add("1440,2408");
        listDimesions.add("1440,2560");
    }

    /**
     * 构造函数 ( 创建文件夹 )
     */
    public GeneratePxValueFiles() {
        File dir = new File(DIR);
        if (!dir.exists()) dir.mkdir();
    }

    /**
     * 生成适配文件
     */
    public void generate() {
        for (String value : listDimesions) {
            String[] wh = value.split(",");
            generateXmlFile(Integer.parseInt(wh[0]), Integer.parseInt(wh[1]));
        }
    }

    /**
     * 生成 xml 文件
     * @param width  宽度
     * @param height 高度
     */
    private void generateXmlFile(
            final int width,
            final int height
    ) {
        // lay_x.xml 数据生成
        StringBuilder widthBuilder = new StringBuilder();
        widthBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        widthBuilder.append("<resources>\n");
        float cellw = width * 1.0F / BASE_WIDTH;
        for (int i = 1; i <= BASE_WIDTH; i++) {
            widthBuilder.append(
                    WIDTH_TEMPLATE.replace("{0}", String.valueOf(i))
                            .replace("{1}", String.valueOf(change(cellw * i)))
            );
        }
        widthBuilder.append("</resources>");

        // lay_y.xml 数据生成
        StringBuilder heightBuilder = new StringBuilder();
        heightBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        heightBuilder.append("<resources>\n");
        float cellh = height * 1.0F / BASE_HEIGHT;
        for (int i = 1; i <= BASE_HEIGHT; i++) {
            heightBuilder.append(
                    HEIGHT_TEMPLATE.replace("{0}", String.valueOf(i))
                            .replace("{1}", String.valueOf(change(cellh * i)))
            );
        }
        heightBuilder.append("</resources>");

        // 文件夹名 values-HxW => values-1334x750
        String folder = VALUE_TEMPLATE.replace("{0}", String.valueOf(height))
                .replace("{1}", String.valueOf(width));
        // 判断文件夹是否创建
        File fileDir = new File(DIR + File.separator + folder);
        fileDir.mkdir();
        // 生成的两个文件
        File layXFile = new File(fileDir.getAbsolutePath(), "lay_x.xml");
        File layYFile = new File(fileDir.getAbsolutePath(), "lay_y.xml");
        try {
            // lay_x.xml
            PrintWriter pw = new PrintWriter(new FileOutputStream(layXFile));
            pw.print(widthBuilder.toString());
            pw.close();
            // lay_y.xml
            pw = new PrintWriter(new FileOutputStream(layYFile));
            pw.print(heightBuilder.toString());
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算适配值
     * @param value 待计算值
     * @return 适配值
     */
    private float change(final float value) {
        int temp = (int) (value * 100);
        return temp / 100F;
    }
}