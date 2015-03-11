package com.afengzi.shardingdatabase.onlydb.multitransaction.impl;


import com.afengzi.shardingdatabase.onlydb.multitransaction.MultiDataSourceManager;
import com.afengzi.shardingdatabase.onlydb.multitransaction.domain.ShardingDataSourceContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 15-1-30
 * Time: ÉÏÎç10:12
 * To change this template use File | Settings | File Templates.
 */
public class MultiDataSourceManagerImpl implements MultiDataSourceManager, ApplicationContextAware, InitializingBean {

    private Map<String, String> dataSourcePool;
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(dataSourcePool)) {
            throw new IllegalArgumentException("dataSourcePool must not empty...");
        }
        for (Map.Entry entry : dataSourcePool.entrySet()) {
            if (!(entry.getValue() instanceof String)) {
                throw new IllegalArgumentException(" the class type of value in dataSourcePool must string...");
            }
            Object obj = applicationContext.getBean(String.valueOf(entry.getValue()));
            if (obj == null) {
                throw new IllegalArgumentException(" the key of value in dataSourcePool is null.key : " + entry.getKey());
            }
            if (!(obj instanceof DataSource)) {
                throw new IllegalArgumentException(" the bean type must dataSource.beanId : " + entry.getValue());
            }
            DataSource dataSource = (DataSource) obj;
            if (!(dataSource instanceof TransactionAwareDataSourceProxy)) {
                ShardingDataSourceContainer.getInstance().addDataSource(String.valueOf(entry.getKey()), new TransactionAwareDataSourceProxy(dataSource));
            }
        }
    }

    public void setDataSourcePool(Map<String, String> dataSourcePool) {
        this.dataSourcePool = dataSourcePool;
    }

    public Map<String, DataSource> getDataSource() {
        return ShardingDataSourceContainer.getInstance().getDataSourceMap() ;
    }

}
