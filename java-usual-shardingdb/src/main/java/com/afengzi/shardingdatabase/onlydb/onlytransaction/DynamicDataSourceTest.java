package com.afengzi.shardingdatabase.onlydb.onlytransaction;


import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 15-2-10
 * Time: обнГ2:06
 * To change this template use File | Settings | File Templates.
 */
public class DynamicDataSourceTest {

    @Test
    public void testSharding(){
        DataSourceIdentificationHolder.setIdentification("11");
//        Long id = sequenceUtil.get("shardingOrderT") ;
//        printLog(id+"");
    }
}
