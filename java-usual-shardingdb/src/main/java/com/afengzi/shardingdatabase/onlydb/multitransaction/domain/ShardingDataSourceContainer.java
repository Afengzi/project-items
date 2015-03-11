package com.afengzi.shardingdatabase.onlydb.multitransaction.domain;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 15-1-30
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
public class ShardingDataSourceContainer {

    private static ShardingDataSourceContainer dataSourceContainer = new ShardingDataSourceContainer();
    private Map<String,DataSource> dataSourceMap = new ConcurrentHashMap<String, DataSource>(20) ;

    public static ShardingDataSourceContainer getInstance(){
        return dataSourceContainer ;
    }

    public void addDataSource(String dataSourceName, DataSource dataSource) {
        if (!dataSourceMap.containsKey(dataSourceName)) {
            dataSourceMap.put(dataSourceName, dataSource);
        }
    }

    /**
     * 拒绝外部对此MAP进行put/remove操作
     * @return
     */
    public Map<String, DataSource> getDataSourceMap() {
        if (dataSourceMap==null||dataSourceMap.isEmpty()){
            dataSourceMap = Collections.emptyMap();
        }
        return Collections.unmodifiableMap(dataSourceMap);
    }

    public DataSource getDataSource(String dataSourceName){
        return dataSourceMap.get(dataSourceName) ;
    }

}
