package dev.standard.vali_phone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dev.utils.DevFinal;
import dev.utils.common.MapUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 验证手机号生成代码
 * @author Ttt
 */
public class ValiPhoneMain {

    public enum Type {

        // 中国移动号码正则
        CHINA_MOBILE_PATTERN(
                "中国移动", "// ==========",
                "134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 195 198"
        ),

        // 中国联通号码正则
        CHINA_UNICOM_PATTERN(
                "中国联通", "// ==========",
                "130 131 132 145 146 155 156 166 167 171 175 176 185 186 196"
        ),

        // 中国电信号码正则
        CHINA_TELECOM_PATTERN(
                "中国电信", "// ==========",
                "133 149 153 173 174 177 180 181 189 190 191 193 199"
        ),

        // 中国广电号码正则
        CHINA_BROADCAST_PATTERN(
                "中国广电", "// ==========",
                "192"
        ),

        // 中国虚拟运营商号码正则
        CHINA_VIRTUAL_PATTERN(
                "虚拟运营商", "// ============",
                "162 165 167 170 171"
        ),

        // 所有中国手机号码正则
        CHINA_PHONE_PATTERN(
                "中国手机", "// ==========", null
        );

        // 运营商名称
        public final String typeName;
        // 分隔符
        public final String symbol;
        // 运营商号码段
        public final String number;

        Type(
                String typeName,
                String symbol,
                String number
        ) {
            this.typeName = typeName;
            this.symbol   = symbol;
            this.number   = number;
        }
    }

    // =

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (Type type : Type.values()) {
            builder.append(generatePATTERN(type));
        }

        String content = builder.toString();
        String debug   = "";
    }

    // ==========
    // = 内部方法 =
    // ==========

    private static final String BUILD_TAB              = "        ";
    private static final String BUILD_TAB_LINE         = DevFinal.SYMBOL.NEW_LINE + BUILD_TAB;
    private static final String BUILD_NEW              = BUILD_TAB_LINE + "builder = new StringBuilder();";
    private static final String BUILD_FORMAT           = BUILD_TAB_LINE + "builder.append(\"%s\");";
    private static final String BUILD_FORMAT_OR        = String.format(BUILD_FORMAT, "|");
    private static final String BUILD_FORMAT_PATTERN   = String.format(
            BUILD_FORMAT, "^%s[%s]{1}\\\\d{8}$"
    );
    private static final String BUILD_FORMAT_TO_STRING = BUILD_TAB_LINE + "%s = builder.toString();";

    /**
     * 获取运行商号码段
     * @param type 运营商类型
     * @return 运行商号码段
     */
    private static String getTypeNumber(final Type type) {
        if (type == Type.CHINA_PHONE_PATTERN) {
            List<String> list = new ArrayList<>();
            for (Type temp : Type.values()) {
                if (temp != Type.CHINA_PHONE_PATTERN) {
                    list.add(temp.number);
                }
            }
            return StringUtils.concatSpiltWithIgnoreLast(
                    DevFinal.SYMBOL.SPACE, list.toArray()
            );
        }
        return type.number;
    }

    /**
     * 待分割运营商号码段转 Map
     * @param numberString 运营商号码段
     * @return 运营商号码段 Map
     */
    private static Map<String, List<String>> toNumberMap(final String numberString) {
        Map<String, List<String>> map = new TreeMap<>();
        if (StringUtils.isNotEmpty(numberString)) {
            String[] arrays = numberString.split(DevFinal.SYMBOL.SPACE);
            for (String number : arrays) {
                // 号码三位数进行裁剪
                String front = number.substring(0, 2);
                String tail  = number.substring(number.length() - 1);
                MapUtils.putToList(map, front, tail);
                Collections.sort(map.get(front));
            }
        }
        return new LinkedHashMap<>(map);
    }

    /**
     * 生成运营商号码正则
     * @param type 运营商类型
     * @return 运营商号码正则
     */
    private static String generatePATTERN(final Type type) {
        StringBuilder builder = new StringBuilder();

        String typeNumber = getTypeNumber(type);
        // 运营商号码段转 Map
        Map<String, List<String>> map = toNumberMap(typeNumber);

        // ==========
        // = 中国移动 =
        // ==========

        builder.append(DevFinal.SYMBOL.NEW_LINE_X2)
                .append(BUILD_TAB).append(type.symbol)
                .append(DevFinal.SYMBOL.NEW_LINE)
                .append(BUILD_TAB).append("// = ")
                .append(type.typeName).append(" =")
                .append(DevFinal.SYMBOL.NEW_LINE)
                .append(BUILD_TAB).append(type.symbol)
                .append(DevFinal.SYMBOL.NEW_LINE);

        // 移动: 134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 195 198
        String[] arrays = typeNumber.split(DevFinal.SYMBOL.SPACE);
        Arrays.sort(arrays);
        String sortNumber = StringUtils.concatSpiltWithIgnoreLast(DevFinal.SYMBOL.SPACE, (Object[]) arrays);
        builder.append(DevFinal.SYMBOL.NEW_LINE).append(BUILD_TAB)
                .append("// ").append(type.typeName)
                .append(": ").append(sortNumber);

        // 正则内容
        builder.append(BUILD_NEW);

        List<String> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String       key   = entry.getKey();
            List<String> value = entry.getValue();
            String numberValue = StringUtils.concatSpiltWithIgnoreLast(
                    DevFinal.SYMBOL.COMMA, value.toArray()
            );
            String annotation = " // " + key + " 开头";
            result.add(String.format(BUILD_FORMAT_PATTERN, key, numberValue) + annotation);
        }
        String resultPATTERN = StringUtils.concatSpiltWithIgnoreLast(
                BUILD_FORMAT_OR, result.toArray()
        );
        builder.append(resultPATTERN)
                .append(String.format(BUILD_FORMAT_TO_STRING, type.name()));
        return builder.toString();
    }
}