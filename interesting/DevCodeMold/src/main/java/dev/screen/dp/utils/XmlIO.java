package dev.screen.dp.utils;

import org.xml.sax.helpers.AttributesImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import dev.utils.DevFinal;

/**
 * detail: dimens 处理
 * @author duke
 */
public class XmlIO {

    /**
     * 解析 dimens 文件
     * @param baseDimenFilePath 源 dimens 文件路径
     */
    public static List<DimenItem> readDimenFile(final String baseDimenFilePath) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser        saxparser        = saxParserFactory.newSAXParser();
            InputStream      inputStream      = new FileInputStream(baseDimenFilePath);
            SAXReadHandler   saxReadHandler   = new SAXReadHandler();
            saxparser.parse(inputStream, saxReadHandler);
            return saxReadHandler.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成 dimens 文件
     * @param isFontMatch 字体是否也适配 ( 是否与 dp 尺寸一样等比缩放 )
     * @param list        源 dimens 数据
     * @param multiple    对应新文件需要乘以的系数
     * @param outPutFile  目标文件输出目录
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createDestinationDimens(
            final boolean isFontMatch,
            final List<DimenItem> list,
            final double multiple,
            final String outPutFile
    ) {
        try {
            File targetFile = new File(outPutFile);
            if (targetFile.exists()) {
                try {
                    targetFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 创建 SAXTransformerFactory 实例
            SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            // 创建 TransformerHandler 实例
            TransformerHandler handler = saxTransformerFactory.newTransformerHandler();
            // 创建 Transformer 实例
            Transformer transformer = handler.getTransformer();
            // 是否自动添加额外的空白
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // 设置字符编码
            transformer.setOutputProperty(OutputKeys.ENCODING, DevFinal.ENCODE.UTF_8);
            // 添加 xml 版本, 默认也是 1.0
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            // 保存 xml 路径
            StreamResult result = new StreamResult(targetFile);
            handler.setResult(result);
            // 创建属性 Attribute 对象
            AttributesImpl attributes = new AttributesImpl();
            attributes.clear();
            // 开始 xml
            handler.startDocument();
            // 换行
            handler.characters("\n".toCharArray(), 0, "\n".length());
            // 写入根节点 resources
            handler.startElement("", "", SAXReadHandler.ELEMENT_RESOURCE, attributes);
            // 集合大小
            int size = list.size();
            for (int i = 0; i < size; i++) {
                DimenItem dimenBean = list.get(i);
                // 乘以系数加上后缀
                String targetValue = Tools.countValue(isFontMatch, dimenBean.value, multiple);
                attributes.clear();
                attributes.addAttribute("", "", SAXReadHandler.PROPERTY_NAME, "", dimenBean.name);

                // 新 dimen 之前换行、缩进
                handler.characters("\n".toCharArray(), 0, "\n".length());
                handler.characters("\t".toCharArray(), 0, "\t".length());

                // 开始标签对输出
                handler.startElement("", "", SAXReadHandler.ELEMENT_DIMEN, attributes);
                handler.characters(targetValue.toCharArray(), 0, targetValue.length());
                handler.endElement("", "", SAXReadHandler.ELEMENT_DIMEN);
            }
            handler.endElement("", "", SAXReadHandler.ELEMENT_RESOURCE);
            handler.endDocument();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}