package dev.standard.dev_final;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.utils.DevFinal;
import dev.utils.common.FileIOUtils;

/**
 * detail: DevFinal.STR 常量格式化排序
 * @author Ttt
 */
public class FinalSortMain {

    public static void main(String[] args) {
        List<String>        sortList  = new ArrayList<>();
        Map<String, String> resultMap = new HashMap<>();
        // 拼接结果数据
        StringBuilder builder = new StringBuilder();
        // 读取待格式化文件内容
        List<String> texts = FileIOUtils.readFileToList(Utils.getFormatFilePath());
        for (String text : texts) {
            if (!text.contains(Utils.PSFS)) {
                Utils.sortAppend(builder, sortList, resultMap);
                builder.append(text).append(DevFinal.SYMBOL.NEW_LINE);
            } else {
                Utils.convertPSFS(text, sortList, resultMap);
            }
        }
        Utils.saveFileByResultTxt(builder);
    }
}