package com.afengzi.shardingdatabase.onlydb.multitransaction;

import com.afengzi.shardingdatabase.onlydb.multitransaction.domain.DataSourceId;
import com.afengzi.shardingdatabase.onlydb.multitransaction.domain.ShardingDataSourceContainer;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.math.NumberUtils;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by lixiuhai on 2015/1/30.
 */
public class SqlMapClientTemplateProxy implements MethodInterceptor {

    private Set<String> filters;

    /**
     * 调用某一个DAO方法之前先获取数据源
     * 再调用SqlMapClientTemplate的父类JdbcAccessor的setDataSource设置数据源
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {

        String methodName = invocation.getMethod().getName();
        System.out.println("################methodName : " + methodName);
        if (!isFilter(methodName)) {
            Object[] args = invocation.getArguments();
            Object thiz = invocation.getThis();
            Class clazz = thiz.getClass();
            Method method = null;
            for (String dataSourceSetter : filters) {
                method = clazz.getMethod(dataSourceSetter, DataSource.class);
                if (method!=null){
                    break;
                }
            }
            if (method==null){
                throw new IllegalArgumentException("can not get dataSource setter...");
            }
            Annotation[][] annotations = invocation.getMethod().getParameterAnnotations();

            for (int i = 0; i < annotations.length; i++) {
                Annotation[] annotation = annotations[i];// locate parameter
                for (Annotation anno : annotation) {       //locate annotation
                    if (anno instanceof DataSourceId) {
                        Object shardingId = args[i];
                        if (shardingId == null) {
                            throw new IllegalArgumentException("must annotate DataSourceId type at parameter shardingId");
                        }
                        method.invoke(thiz, getDataSourceByBeanId(NumberUtils.toLong(String.valueOf(shardingId))));
                    }

                }
            }

        }

        return invocation.proceed();
    }

    /**
     * 获取数据源
     *
     * @param shardingId
     * @return
     */
    private DataSource getDataSourceByBeanId(long shardingId) {
        String dataSourceName = ShardingDatabaseUtils.getDataSourceId(shardingId);
        return ShardingDataSourceContainer.getInstance().getDataSource(dataSourceName);
    }

    private boolean isFilter(String methodName) {
        if (filters.contains(methodName)) {
            return true;
        }
        return false;
    }

    public void setFilters(Set<String> filters) {
        this.filters = filters;
    }
}
