package dev.standard.dev_final;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.utils.DevFinal;
import dev.utils.common.FileIOUtils;

/**
 * detail: DevFinal 常量格式化排序
 * @author Ttt
 * <pre>
 *     复制 DevFinal 文件代码到 format.txt 中
 *     然后运行 main 方法后 copy result.txt 内容覆盖 DevFinal 类代码
 * </pre>
 */
public class FinalSortMain2 {

    public static void main(String[] args) {
        // 标记状态
        boolean tagState = false;
        // 排序常量信息
        List<String>        sortList  = new ArrayList<>();
        Map<String, String> resultMap = new HashMap<>();
        // 拼接结果数据
        StringBuilder builder = new StringBuilder();
        // 读取待格式化文件内容
        List<String> texts = FileIOUtils.readFileToList(Utils.getFormatFilePath());
        for (String text : texts) {
            // 如果是起点
            if (text.contains(Utils.START_TAG)) {
                tagState = true;
            } else if (tagState) {
                if (text.contains(Utils.END_TAG)) {
                    Utils.sortAppend(builder, sortList, resultMap);
                    tagState = false;
                }
            }
            if (tagState) {
                if (!text.contains(Utils.PSFS)) {
                    Utils.sortAppend(builder, sortList, resultMap);
                    builder.append(text).append(DevFinal.SYMBOL.NEW_LINE);
                } else {
                    Utils.convertPSFS(text, sortList, resultMap);
                }
            } else {
                builder.append(text).append(DevFinal.SYMBOL.NEW_LINE);
            }
        }
        Utils.saveFileByResultTxt(builder);
    }
}