package com.afengzi.automation.generator;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixiuhai on 2015/3/4.
 */
public class OridinaryBeanConfigManager extends AbstractLoadXml {


    public OridinaryBeanConfigManager(String path) {
        initDocument(path);
    }

    public Map<String, OrdinaryBeanConfig> beanMap(String clazzName) {
        NodeList nodeList = parseXml();
        Map<String, OrdinaryBeanConfig> beanMap = new HashMap<String, OrdinaryBeanConfig>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            OrdinaryBeanConfig ordinaryBeanConfig = new OrdinaryBeanConfig();
            Element element = (Element) nodeList.item(i);
            String beanClazz = element.getAttribute(BEAN_CLASS);
            System.out.println("####### "+beanClazz);
            if (!clazzName.equals(beanClazz)) {
               continue;
            }

            String beanId = element.getAttribute("id");

            ordinaryBeanConfig.setBeanId(beanId);
            ordinaryBeanConfig.setClazz(beanClazz);
            ordinaryBeanConfig.setPropertyMap(parseElement(element));
            beanMap.put(beanId, ordinaryBeanConfig);
        }
        return beanMap;
    }

    public static void main(String[] args) {
        OridinaryBeanConfigManager oridinaryBeanConfigManager = new OridinaryBeanConfigManager("sharding-database\\src\\main\\resources\\com\\afengzi\\shardingdatabase\\onlydb\\multitransaction\\spring-config-task-process.xml");
        oridinaryBeanConfigManager.parseXml();
        System.out.println("$$$$$$$$$$ " + oridinaryBeanConfigManager.beanMap(TASK_PROCESS_CLAZZ));
    }
}
