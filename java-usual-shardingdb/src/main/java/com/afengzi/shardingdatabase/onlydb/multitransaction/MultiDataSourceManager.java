package com.afengzi.shardingdatabase.onlydb.multitransaction;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 15-1-30
 * Time: ионГ11:32
 * To change this template use File | Settings | File Templates.
 */
public interface MultiDataSourceManager {

    public Map<String,DataSource> getDataSource();
}
