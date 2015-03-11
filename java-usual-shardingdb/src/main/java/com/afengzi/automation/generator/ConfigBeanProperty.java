package com.afengzi.automation.generator;

import java.util.List;
import java.util.Map;

/**
 * Created by lixiuhai on 2015/3/3.
 */
public class ConfigBeanProperty {
    private String beanId ;
    private String clazz ;
    private String subBean ;
    private Map<String,String> propertyMap ;
    private Map<String,List<ConfigBeanProperty>> listPropertyMap;

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

    public String getSubBean() {
        return subBean;
    }

    public void setSubBean(String subBean) {
        this.subBean = subBean;
    }

    public Map<String, String> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(Map<String, String> propertyMap) {
        this.propertyMap = propertyMap;
    }

    public Map<String, List<ConfigBeanProperty>> getListPropertyMap() {
        return listPropertyMap;
    }

    public void setListPropertyMap(Map<String, List<ConfigBeanProperty>> listPropertyMap) {
        this.listPropertyMap = listPropertyMap;
    }

    @Override
    public String toString() {
        return "ConfigBeanProperty{" +
                "beanId='" + beanId + '\'' +
                ", clazz='" + clazz + '\'' +
                ", subBean='" + subBean + '\'' +
                ", propertyMap=" + propertyMap +
                ", listPropertyMap=" + listPropertyMap +
                '}';
    }
}
