package com.afengzi.automation.generator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * Created by lixiuhai on 2015/2/28.
 */
public class SpringBeanConfigManager extends DomUtils {
    private final static Logger log = Logger.getLogger(SpringBeanConfigManager.class);
    private static final String TEMPLATE_DIR = "template.dir";
    private static final String TEMPLATE_NAME = "template.name";
    private static final String TEMPLATE_SUFFIX = "template.suffix";

    private static final String BEAN_CLASS = "class";
    private static final String BEAN_ID = "id";
    private static final String SUB_BEAN = "bean";
    private static final String PROPERTY = "property";
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String REF = "value";
    public static final String LIST = "list";
    public static final String LIST_SUFFIX = "list";

    private Document document;
    private String templateDir;
    private String templateName;

    public SpringBeanConfigManager() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse("sharding-database\\src\\main\\resources\\com\\afengzi\\shardingdatabase\\onlydb\\multitransaction\\spring-config-service.xml");
//            readProperties();
        } catch (ParserConfigurationException e) {
            log.error("create document error.", e);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, ConfigBeanProperty> springBeanMap() {
        NodeList nodeList = document.getElementsByTagName("bean");
        return beanMap(nodeList);
    }

    private Map<String, ConfigBeanProperty> beanMap(NodeList nodeList) {
        Map<String, ConfigBeanProperty> beanMap = new HashMap<String, ConfigBeanProperty>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String beanId = element.getAttribute("id");
            beanMap.put(beanId, elementToConfigBeanProperty(element));
        }
        return beanMap;
    }


//    public Map<String, String> getBeanPropertyById(String beanId) {
//        if (StringUtils.isBlank(beanId)) {
//            return null;
//        }
//        Map<String, Map<String, String>> springBean = springBeanMap();
//        if (springBean.containsKey(beanId)) {
//            return springBean.get(beanId);
//        }
//        return null;
//    }

    private ConfigBeanProperty elementToConfigBeanProperty(Element element) {

        ConfigBeanProperty configBeanProperty = new ConfigBeanProperty();
        NodeList propertyList = element.getElementsByTagName(PROPERTY);
        configBeanProperty.setBeanId(element.getAttribute(BEAN_ID));
        configBeanProperty.setClazz(element.getAttribute(BEAN_CLASS));

        Map<String, String> propertyMap = new HashMap<String, String>();
        for (int i = 0; i < propertyList.getLength(); i++) {
            Element subElement = (Element) propertyList.item(i);
            if (subElement.hasAttribute(NAME)) {
                if (subElement.getAttribute(NAME).equals("rejectedExecutionHandler")){
                    System.out.println("#################");
                }
                if (hasOridinaryAttriValue(subElement)) {
                    propertyMap.put(subElement.getAttribute(NAME), getOridinaryAttributeValue(subElement));
                }

                if (hasSubBeanAttriValue(subElement)) {
                    System.out.println("#################");
                    propertyMap.put(subElement.getAttribute(NAME), getSubBean(subElement));
                }
                if (hasSubListBeans(subElement, subElement.getAttribute(NAME))) {
                    System.out.println("*******************");
                    configBeanProperty.setListPropertyMap(getSubBeans(element, subElement.getAttribute(NAME)));
                }
            }
        }
        configBeanProperty.setPropertyMap(propertyMap);
        return configBeanProperty;
    }

    private boolean hasOridinaryAttriValue(Element element) {
        return StringUtils.isNotBlank(getOridinaryAttributeValue(element));
    }

    private boolean hasSubBeanAttriValue(Element element) {
        Element subElement = getChildElementByTagName(element, SUB_BEAN);
        return subElement != null;
    }

    private boolean hasSubListBeans(Element element, String attribute) {
       return getChildElementByTagName(element,LIST)!=null;
    }

    private String getOridinaryAttributeValue(Element element) {
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

    private String getSubBean(Element element) {
        NodeList nodeList = element.getElementsByTagName(SUB_BEAN) ;
        if (nodeList!=null&&nodeList.getLength()>0){
            Element subEle = (Element) nodeList.item(0);
            return subEle.getAttribute(BEAN_CLASS);
        }
        return "" ;
    }

    private Map<String, List<ConfigBeanProperty>> getSubBeans(Element element, String attribute) {
        Map<String, List<ConfigBeanProperty>> subBeansMap = new HashMap<String, List<ConfigBeanProperty>>();
        NodeList listList = element.getElementsByTagName(LIST);
        if (listList != null) {
            subBeansMap.put(attribute, mapValues(beanMap(listList)));
        }
        return null;
    }

    private List<ConfigBeanProperty> mapValues(Map<String, ConfigBeanProperty> map) {
        List<ConfigBeanProperty> configBeanProperties = new ArrayList<ConfigBeanProperty>();
        for (Map.Entry<String, ConfigBeanProperty> entry : map.entrySet()) {
            configBeanProperties.add(entry.getValue());
        }
        return configBeanProperties;
    }


    private void readProperties() {
        Properties properties = new Properties();

        try {
            InputStream inputStream = getClass().getResourceAsStream("com/afengzi/automation/generator/config.properties");
            properties.load(inputStream);
            templateDir = properties.getProperty(TEMPLATE_DIR);
            templateName = properties.getProperty(TEMPLATE_NAME);
        } catch (FileNotFoundException e) {
            log.error(" no find config.properties file .", e);
        } catch (IOException e) {
            log.error("Properties load inputStream error.", e);
        }
    }

    public static void main(String[] args) {
        SpringBeanConfigManager springBeanConfigManager = new SpringBeanConfigManager();
        System.out.println(springBeanConfigManager.springBeanMap());
    }
}
