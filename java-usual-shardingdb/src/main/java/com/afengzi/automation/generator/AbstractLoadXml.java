package com.afengzi.automation.generator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiuhai on 2015/3/4.
 */
public abstract class AbstractLoadXml {
    protected final static Logger log = Logger.getLogger(OridinaryBeanConfigManager.class);
    protected static final String BEAN_CLASS = "class";
    protected static final String BEAN_ID = "id";
    protected static final String SUB_BEAN = "bean";
    protected static final String PROPERTY = "property";
    protected static final String NAME = "name";
    protected static final String VALUE = "value";
    protected static final String REF = "value";
    public static final String LIST = "list";
    public static final String LIST_SUFFIX = "list";
    public static final String THREAD_POOL_CLAZZ = "org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor";
    public static final String TASK_PROCESS_CLAZZ = "com.jd.chongzhi.service.task.MultiTaskProcessor";

    protected Document document;


    protected void initDocument(String parth) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse(parth);
        } catch (ParserConfigurationException e) {
            log.error("create document error.", e);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NodeList parseXml() {
         return document.getElementsByTagName("bean");
    }

    protected String getOridinaryAttributeValue(Element element) {
        if (element.hasAttribute(VALUE)) {
            return element.getAttribute(VALUE);
        } else if (element.hasAttribute(REF)) {
            return element.getAttribute(REF);
        } else if (element.hasAttribute(BEAN_CLASS)) {
            return element.getAttribute(BEAN_CLASS);
        } else {
            return "";
        }
    }

    protected boolean hasOridinaryAttriValue(Element element) {
        return StringUtils.isNotBlank(getOridinaryAttributeValue(element));
    }

    protected Map<String,String> parseElement(Element element) {
        Map<String, String> propertyMap = new HashMap<String, String>();
        NodeList propertyList = element.getElementsByTagName(PROPERTY);
        for (int i = 0; i < propertyList.getLength(); i++) {
            Element subElement = (Element) propertyList.item(i);
            if (subElement.hasAttribute(NAME)) {
                if (hasOridinaryAttriValue(subElement)) {
                    propertyMap.put(subElement.getAttribute(NAME), getOridinaryAttributeValue(subElement));
                }
            }
        }
        return propertyMap ;
    }

}
