package dev.dimens;

import java.math.BigDecimal;

import dev.utils.DevFinal;
import dev.utils.common.BigDecimalUtils;

/**
 * detail: dimen 值生成
 * @author Ttt
 */
public class DimensValueMain {

    public static void main(String[] args) {
        // 生成字体 sp dimen 值
        System.out.println(generateDimen(5F, 100F, 0.5F, false, "sp"));

        // 生成正数 dp dimen 值
        System.out.println(generateDimen(0F, 1920F, 0.5F, false, "dp"));

        // 生成负数 dp dimen 值 => -0.5 ~ -50.0dp
        System.out.println(generateDimen(0.5F, 50F, 0.5F, true, "dp"));
    }

    /**
     * 生成 dimen 值
     * @param start      起点值
     * @param end        结束值
     * @param interval   间隔值
     * @param isNegative 是否负数
     * @param unit       单位
     * @return dimen 值
     */
    private static String generateDimen(
            final float start,
            final float end,
            final float interval,
            final boolean isNegative,
            final String unit
    ) {
        String format = "<dimen name=\"%s\">%s%s%s</dimen>";
        // 正负单位结尾后缀
        String suffix = isNegative ? "_n" : "";
        // 正负符号
        String symbol = (isNegative ? "-" : "");

        StringBuilder builder = new StringBuilder();
        builder.append(DevFinal.SYMBOL.NEW_LINE_X2);
        // 生成正数的
        for (float value = start; value <= end; value += interval) {
            int    intValue = (int) value;
            String strValue = String.valueOf(value);
            strValue = BigDecimalUtils.operation(strValue)
                    .round(1, BigDecimal.ROUND_HALF_UP)
                    .toPlainString();
            // 属于整数
            if (strValue.endsWith(".0")) {
                strValue = String.valueOf(intValue);
            } else {
                strValue = strValue.replaceAll("\\.", "_");
            }
            builder.append(
                    String.format(
                            format, unit + "_" + strValue + suffix,
                            symbol, value, unit
                    )
            ).append(DevFinal.SYMBOL.NEW_LINE);
        }
        return builder.toString();
    }
}