package com.afengzi.shardingdatabase.onlydb.onlytransaction;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 15-2-10
 * Time: обнГ1:12
 * To change this template use File | Settings | File Templates.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceIdentificationHolder.getIdentification() ;
    }
}
