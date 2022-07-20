package dev.standard.generate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import dev.utils.DevFinal;
import dev.utils.common.StringUtils;

/**
 * detail: IntentData 类创建
 * @author Ttt
 * <pre>
 *     专门用于根据 {@link DevFinal.STR} 常量
 *     生成 IntentData 类快捷方法 ( DevFinal.STR Intent 传参读写辅助类 )
 * </pre>
 */
public class IntentDataKotlinGenerateMain {

    // 方法字符串
    private static final String METHOD_STR;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("\n    // =");
        builder.append("\n");
        builder.append("\n    /**");
        builder.append("\n     * 获取 Key ( %s ) 对应的 Value");
        builder.append("\n     * @return Value");
        builder.append("\n     */");
        builder.append("\n    fun get%s(): String? {");
        builder.append("\n        return get(DevFinal.STR.%s)");
        builder.append("\n    }");
        builder.append("\n");
        builder.append("\n    /**");
        builder.append("\n     * 设置 Key ( %s ) 对应的 Value");
        builder.append("\n     * @param value 保存的 value");
        builder.append("\n     * @return IntentData");
        builder.append("\n     */");
        builder.append("\n    fun set%s(value : String?): IntentData {");
        builder.append("\n        return put(DevFinal.STR.%s, value)");
        builder.append("\n    }");
        builder.append("\n");
        builder.append("\n    /**");
        builder.append("\n     * 移除 Key ( %s )");
        builder.append("\n     * @return IntentData");
        builder.append("\n     */");
        builder.append("\n    fun remove%s(): IntentData {");
        builder.append("\n        return remove(DevFinal.STR.%s)");
        builder.append("\n    }");
        builder.append("\n");
        builder.append("\n    /**");
        builder.append("\n     * 是否存在 Key ( %s )");
        builder.append("\n     * @return `true` yes, `false` no");
        builder.append("\n     */");
        builder.append("\n    fun contains%s(): Boolean {");
        builder.append("\n        return containsKey(DevFinal.STR.%s)");
        builder.append("\n    }");
        builder.append("\n");
        builder.append("\n    /**");
        builder.append("\n     * Key ( %s ) 保存的 Value 是否为 null");
        builder.append("\n     * @return `true` yes, `false` no");
        builder.append("\n     */");
        builder.append("\n    fun isNullValue%s(): Boolean {");
        builder.append("\n        return isNullValue(DevFinal.STR.%s)");
        builder.append("\n    }");
        METHOD_STR = builder.toString();
    }

    private static final HashSet<String> sIgnoreSet = new HashSet<>();

    public static void main(String[] args) {
        sIgnoreSet.addAll(DevFinalIgnore.ignoreSet());
        // 循环所有常量
        List<String> lists  = new ArrayList<>();
        Field[]      fields = DevFinal.STR.class.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String name      = StringUtils.upperFirstLetter(fieldName.toLowerCase());
            lists.add(name);
        }

        // ==========
        // = 开始拼接 =
        // ==========

        StringBuilder builder = new StringBuilder();
        for (String name : lists) {
            // 判断是否忽略常量
            if (sIgnoreSet.contains(name)) {
                continue;
            }

            String finalName     = StringUtils.underScoreCaseToCamelCase(name);
            String nameUpperCase = name.toUpperCase();

            // class 特殊处理防止 getClass
            if ("class".equalsIgnoreCase(finalName)) {
                finalName = finalName.toUpperCase();
            }

            builder.append(DevFinal.SYMBOL.NEW_LINE)
                    .append(String.format(
                            METHOD_STR,
                            nameUpperCase, finalName, nameUpperCase,
                            nameUpperCase, finalName, nameUpperCase,
                            nameUpperCase, finalName, nameUpperCase,
                            nameUpperCase, finalName, nameUpperCase,
                            nameUpperCase, finalName, nameUpperCase
                    ));
        }
        // 最终内容
        String content = builder.toString();

        String debug = "";
    }
}