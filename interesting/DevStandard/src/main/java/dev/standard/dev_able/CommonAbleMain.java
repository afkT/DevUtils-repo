package dev.standard.dev_able;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.utils.DevFinal;

/**
 * detail: Common able 文件生成入口
 * @author Ttt
 */
public class CommonAbleMain {

    public static void main(String[] args) {
        Utils.generateAbleFile(REPLACE_LIST);

        String ignore = getIgnoreAbleCLass();

        String debug = "";
    }

    private static final List<Utils.Replace> REPLACE_LIST;

    static {
        List<Utils.Replace> list = new ArrayList<>();
        list.add(new Utils.Replace("Get", "get", returnGetAbleAnnotation()));
        list.add(new Utils.Replace("By", "by"));
        list.add(new Utils.Replace("To", "to"));
        list.add(new Utils.Replace("Flow", "flow"));
        list.add(new Utils.Replace("Sort", "sort"));
        list.add(new Utils.Replace("Calculate", "calculate"));
        list.add(new Utils.Replace("Call", "callback"));
        list.add(new Utils.Replace("Method", "invoke"));
        list.add(new Utils.Replace("Clone", "cloneMethod"));
        list.add(new Utils.Replace("Close", "close"));
        list.add(new Utils.Replace("Convert", "convert"));
        list.add(new Utils.Replace("Correct", "correct"));
        list.add(new Utils.Replace("Decode", "decode"));
        list.add(new Utils.Replace("Encode", "encode"));
        list.add(new Utils.Replace("Decrypt", "decrypt"));
        list.add(new Utils.Replace("Encrypt", "encrypt"));
        list.add(new Utils.Replace("Filter", "accept"));
        list.add(new Utils.Replace("Intercept", "intercept"));
        list.add(new Utils.Replace("IO", "io"));
        list.add(new Utils.Replace("Notify", "notifyMethod"));
        list.add(new Utils.Replace("Operate", "execute"));
        list.add(new Utils.Replace("Input", "input"));
        list.add(new Utils.Replace("Output", "output"));
        list.add(new Utils.Replace("Read", "read"));
        list.add(new Utils.Replace("Write", "write"));
        list.add(new Utils.Replace("Refresh", "refresh"));
        list.add(new Utils.Replace("Replace", "replace"));
        list.add(new Utils.Replace("Search", "search"));
        list.add(new Utils.Replace("Query", "query"));
        list.add(new Utils.Replace("Find", "find"));
        list.add(new Utils.Replace("Split", "split"));
        list.add(new Utils.Replace("Task", "result"));
        list.add(new Utils.Replace("Router", "router"));
        list.add(new Utils.Replace("Error", "tryCatch"));
        list.add(new Utils.Replace("Thread", "execute"));
        list.add(new Utils.Replace("Editor", "edit"));
        list.add(new Utils.Replace("Copy", "copy"));
        list.add(new Utils.Replace("Paste", "paste"));
        list.add(new Utils.Replace("Modify", "modify"));
        list.add(new Utils.Replace("Parse", "parse"));
        list.add(new Utils.Replace("Send", "post"));
        REPLACE_LIST = Collections.unmodifiableList(list);
    }

    private static String returnGetAbleAnnotation() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n *     <p></p>");
        builder.append("\n *     其他类全部都是 copy {@link Getable} 代码完全一致, 只有方法名、接口名不同");
        builder.append("\n *     不通用 {@link Getable} 接口是为了区分功能, 对常用的功能定义对应类");
        builder.append("\n *     如 Readable、Writeable 读写较为常用, 所以专门定义对应接口类");
        builder.append("\n *     <p></p>");
        builder.append("\n *     正常如 {@link Writeable} write() 方法需要返回写入结果, 可自行传入 <T> 泛型为 Boolean");
        builder.append("\n *     也能自行决定需要返回什么类型值");
        return builder.toString();
    }

    private static String getIgnoreAbleCLass() {
        StringBuilder builder = new StringBuilder();
        for (Utils.Replace info : REPLACE_LIST) {
            builder.append(DevFinal.SYMBOL.NEW_LINE)
                    .append("        sFilterClassSet_APP.add(\"")
                    .append(info.getFileName())
                    .append("\");");
        }
        return builder.toString();
    }
}