package com.afengzi.automation.generator;

import java.util.Map;

/**
 * Created by lixiuhai on 2015/3/4.
 */
public class OrdinaryBeanConfig {
    private String beanId ;
    private String clazz ;
    private Map<String,String> propertyMap ;

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Map<String, String> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(Map<String, String> propertyMap) {
        this.propertyMap = propertyMap;
    }

    @Override
    public String toString() {
        return "OrdinaryBeanConfig{" +
                "beanId='" + beanId + '\'' +
                ", clazz='" + clazz + '\'' +
                ", propertyMap=" + propertyMap +
                '}';
    }
}
