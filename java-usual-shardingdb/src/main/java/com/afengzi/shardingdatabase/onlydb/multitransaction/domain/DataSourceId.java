package com.afengzi.shardingdatabase.onlydb.multitransaction.domain;

import java.lang.annotation.*;

/**
 * Created by lixiuhai on 2015/1/30.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface DataSourceId {
    public String key() default "shardingId" ;
}
