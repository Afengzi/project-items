package com.afengzi.shardingdatabase.onlydb.multitransaction;

/**
 * Created by lixiuhai on 2015/1/30.
 */
public class ShardingDatabaseUtils {

    public static final String DATABASE_ID_PREFIX = "afengziDataSource_" ;

    public static String getDataSourceId(long shardingId){
        String flag = String.valueOf(shardingId).substring(0,2) ;
        return DATABASE_ID_PREFIX+flag ;
    }
}
