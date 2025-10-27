package dev.screen.dp.utils;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * detail: 解析 xml 工具类
 * @author duke
 */
public class SAXReadHandler
        extends DefaultHandler {

    private      ArrayList<DimenItem> list             = null;
    private      DimenItem            dimenBean;
    private      String               tempElement;
    static final String               ELEMENT_RESOURCE = "resources";
    static final String               ELEMENT_DIMEN    = "dimen";
    static final String               PROPERTY_NAME    = "name";

    public List<DimenItem> getData() {
        return list;
    }

    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes
    ) {
        tempElement = qName;
        if (qName != null && qName.trim().length() > 0) {
            if (ELEMENT_RESOURCE.equals(qName)) {
                list = new ArrayList<>();
            } else if (ELEMENT_DIMEN.equals(qName)) {
                dimenBean = new DimenItem();
                if (attributes != null && attributes.getLength() > 0) {
                    dimenBean.name = attributes.getValue(PROPERTY_NAME);
                }
            }
        }
    }

    @Override
    public void endElement(
            String uri,
            String localName,
            String qName
    ) {
        if (qName != null && qName.trim().length() > 0) {
            if (ELEMENT_DIMEN.equals(qName)) {
                // dimen 结束标签, 添加对象到集合
                if (list != null) {
                    list.add(dimenBean);
                    dimenBean = null;
                }
            }
        }
    }

    @Override
    public void characters(
            char[] ch,
            int start,
            int length
    ) {
        if (tempElement != null && tempElement.trim().equals(ELEMENT_DIMEN)) {
            if (dimenBean != null) {
                String temp = new String(ch, start, length);
                if (temp.trim().length() > 0) {
                    temp = temp.trim();
                    if (dimenBean.value == null || dimenBean.value.trim().length() == 0) {
                        dimenBean.value = temp;
                    } else {
                        dimenBean.value += temp;
                    }
                }
            }
        }
    }
}