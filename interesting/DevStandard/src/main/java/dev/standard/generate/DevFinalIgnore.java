package dev.standard.generate;

import java.lang.reflect.Field;
import java.util.HashSet;

import dev.utils.common.StringUtils;

/**
 * detail: 常量类
 * @author Ttt
 */
final class DevFinalIgnore {

    public static HashSet<String> ignoreSet() {
        HashSet<String> hashSet = new HashSet<>();
        Field[]         fields  = STR.class.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String name      = StringUtils.upperFirstLetter(fieldName.toLowerCase());
            hashSet.add(name);
        }
        return hashSet;
    }

    /**
     * detail: String 类型常量
     * @author Ttt
     */
    public static final class STR {

        // =======
        // = 通用 =
        // =======

        public static final String BUG = "bug";

        public static final String VO = "vo";

        public static final String DB = "db";

        // =======
        // = 其他 =
        // =======

        public static final String BANK   = "bank";
        public static final String INDENT = "indent";
        public static final String TIMING = "timing";

        // ==========
        // = 信息相关 =
        // ==========

        // =======
        // = 媒体 =
        // =======

        public static final String IMAGES = "images";

        public static final String AAC  = "aac";
        public static final String GIF  = "gif";
        public static final String JPG  = "jpg";
        public static final String JSON = "json";
        public static final String MP3  = "mp3";
        public static final String MP4  = "mp4";
        public static final String PNG  = "png";
        public static final String TXT  = "txt";
        public static final String WEBP = "webp";
        public static final String XML  = "xml";

        // ==========
        // = 时间相关 =
        // ==========

        // ===============
        // = 状态、操作相关 =
        // ===============

        public static final String UN_BINDER = "un_binder";

        public static final String ASYNC      = "async";
        public static final String CONNECT    = "connect";
        public static final String DELAY      = "delay";
        public static final String DENIED     = "denied";
        public static final String DISCONNECT = "disconnect";
        public static final String DISK       = "disk";
        public static final String GRANTED    = "granted";
        public static final String LOAD       = "load";
        public static final String PERIOD     = "period";
        public static final String UNCONNECT  = "unconnect";

        // ==========
        // = 平台相关 =
        // ==========

        public static final String ANDROID      = "android";
        public static final String H5           = "h5";
        public static final String IOS          = "ios";
        public static final String MIN_IPROGRAM = "min_iprogram";
        public static final String WEB          = "web";

        // ===============
        // = UI、APP 相关 =
        // ===============

        public static final String ANIMATION = "animation";
        public static final String CHECKBOX  = "checkbox";

        public static final String EXTRA = "extra";

        public static final String LIVE_DATA  = "live_data";
        public static final String VIEW_MODEL = "view_model";

        public static final String RICH_TEXT = "rich_text";
        public static final String TRANSFORM = "transform";

        // ==========
        // = 数据相关 =
        // ==========

        public static final String FALSE = "false";
        public static final String TRUE  = "true";

        public static final String BOOLEAN = "boolean";
        public static final String BYTE    = "byte";
        public static final String CHAR    = "char";
        public static final String DOUBLE  = "double";
        public static final String FLOAT   = "float";
        public static final String INT     = "int";
        public static final String INTEGER = "integer";
        public static final String LONG    = "long";

        // ========
        // = 关键字 =
        // ========

        public static final String CATCH     = "catch";
        public static final String ERROR     = "error";
        public static final String THROWABLE = "throwable";
        public static final String TRY       = "try";

        public static final String CYCLE = "cycle";

        public static final String AGENT   = "agent";
        public static final String ALIAS   = "alias";
        public static final String MISSING = "missing";

        public static final String CONST     = "const";
        public static final String FIELD     = "field";
        public static final String FINAL     = "final";
        public static final String FOR       = "for";
        public static final String INTERFACE = "interface";
        public static final String NEW       = "new";
        public static final String NULL      = "null";
        public static final String PARAM     = "param";
        public static final String VAL       = "val";
        public static final String VAR       = "var";
        public static final String VOID      = "void";
        public static final String WHILE     = "while";
    }
}