package dev.standard.generate;

import dev.utils.DevFinal;

/**
 * detail: dimen.xml dp、sp 循环创建
 * @author Ttt
 */
public class DimenGenerateMain {

    private static final String SPACE    = "    ";
    private static final String DP_DIMEN = "<dimen name=\"dp_%s\">%sdp</dimen>";
    private static final String SP_DIMEN = "<dimen name=\"sp_%s\">%ssp</dimen>";

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append(forDimen(750, DP_DIMEN, "DP"));
        builder.append(DevFinal.SYMBOL.NEW_LINE_X2);
        builder.append(forDimen(80, SP_DIMEN, "SP"));

        // 最终内容
        String content = builder.toString();

        String debug = "";
    }

    /**
     * 循环创建 dimen 值
     * <pre>
     *     从 0 开始创建, 间隔 0.5
     * </pre>
     * @param max    最大值
     * @param format 格式化字符串
     * @param title  标题
     * @return 拼接后的值
     */
    private static StringBuilder forDimen(
            final int max,
            final String format,
            final String title
    ) {
        StringBuilder builder = new StringBuilder();
        // <!-- 通用 SP max = 100 -->
        builder.append(SPACE).append("<!-- 通用 ")
                .append(title).append(" max = ").append(max)
                .append(" -->");
        for (int i = 0; i < max; i++) {
            String is      = String.valueOf(i);
            String value   = String.format(format, is, is + ".0");
            String value_5 = String.format(format, is + "_5", is + ".5");
            builder.append(DevFinal.SYMBOL.NEW_LINE)
                    .append(SPACE).append(value);
            builder.append(DevFinal.SYMBOL.NEW_LINE)
                    .append(SPACE).append(value_5);
        }
        // 结尾以 max 为结尾
        String is    = String.valueOf(max);
        String value = String.format(format, is, is + ".0");
        builder.append(DevFinal.SYMBOL.NEW_LINE)
                .append(SPACE).append(value);
        return builder;
    }
}