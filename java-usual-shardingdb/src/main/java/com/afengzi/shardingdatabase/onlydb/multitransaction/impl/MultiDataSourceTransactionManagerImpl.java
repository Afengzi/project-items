package com.afengzi.shardingdatabase.onlydb.multitransaction.impl;


import com.afengzi.shardingdatabase.onlydb.multitransaction.MultiDataSourceManager;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 15-1-30
 * Time: ÉÏÎç11:20
 * To change this template use File | Settings | File Templates.
 */
public class MultiDataSourceTransactionManagerImpl extends
        AbstractPlatformTransactionManager implements InitializingBean {
    private final static Logger log = Logger.getLogger(MultiDataSourceTransactionManagerImpl.class);

    private MultiDataSourceManager multiDataSourceManager;
    private List<PlatformTransactionManager> transactionManagers = new ArrayList<PlatformTransactionManager>();

    @Override
    protected Object doGetTransaction() throws TransactionException {
        return new ArrayList<DefaultTransactionStatus>();
    }

    /**
     * We need to disable transaction synchronization so that the shared
     * transaction synchronization state will not collide with each other. BUT,
     * for LOB creators to use, we have to pay attention here:
     * <ul>
     * <li>if the LOB creator use standard preparedStatement methods, this
     * transaction synchronization setting is OK;</li>
     * <li>if the LOB creator don't use standard PS methods, you have to find
     * other way to make sure the resources your LOB creator used should be
     * cleaned up after the transaction.</li>
     * </ul>
     */
    @Override
    protected void doBegin(Object transactionObject,
                           TransactionDefinition transactionDefinition)
            throws TransactionException {
        @SuppressWarnings("unchecked")
        List<DefaultTransactionStatus> list = (List<DefaultTransactionStatus>) transactionObject;
        //Modified by zxf.  Do rollback when exception to ensure release any resource or restore any status during doBegin on succeeded data source.
        try {
            for (PlatformTransactionManager transactionManager : transactionManagers) {
                DefaultTransactionStatus element = (DefaultTransactionStatus) transactionManager
                        .getTransaction(transactionDefinition);
                list.add(element);
            }
        } catch (TransactionException e) {
            logger.error("Caught an exception during doBegin on multiple data sources", e);
            //Construct a new TransactionStatus with success transactionStatus List and then rollback them.
            boolean newSynchronization = (getTransactionSynchronization() != SYNCHRONIZATION_NEVER);
            TransactionStatus transactionStatus = newTransactionStatus(transactionDefinition, list, true, newSynchronization, logger.isDebugEnabled(), null);
            rollback(transactionStatus);
            throw e;
        }
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
        @SuppressWarnings("unchecked")
        List<DefaultTransactionStatus> list =
                (List<DefaultTransactionStatus>) status.getTransaction();

        logger.debug("prepare to commit transactions on multiple data sources.");
        Validate.isTrue(list.size() <= this.getTransactionManagers().size());

        TransactionException lastException = null;
        for (int i = list.size() - 1; i >= 0; i--) {
            PlatformTransactionManager transactionManager = this.getTransactionManagers().get(i);
            TransactionStatus localTransactionStatus = list.get(i);

            try {
                transactionManager.commit(localTransactionStatus);
            } catch (TransactionException e) {
                lastException = e;
                logger.error("Error in commit", e);

            }
        }
        if (lastException != null) {
            throw lastException;
            // Rollback will ensue as long as rollbackOnCommitFailure=true
        }

    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
        @SuppressWarnings("unchecked")
        List<DefaultTransactionStatus> list =
                (List<DefaultTransactionStatus>) status.getTransaction();

        logger.info("prepare to rollback transactions on multiple data sources.");
        Validate.isTrue(list.size() <= this.getTransactionManagers().size());

        TransactionException lastException = null;
        for (int i = list.size() - 1; i >= 0; i--) {
            PlatformTransactionManager transactionManager = this.getTransactionManagers().get(i);
            TransactionStatus localTransactionStatus = list.get(i);

            try {
                transactionManager.rollback(localTransactionStatus);
            } catch (TransactionException e) {
                // Log exception and try to complete rollback
                lastException = e;
                logger.error("error occured when rolling back the transaction. \n{}", e);
            }
        }

        if (lastException != null) {
            throw lastException;
        }
    }


    public void afterPropertiesSet() throws Exception {
        Validate.notNull(multiDataSourceManager);
        for (Map.Entry entry : multiDataSourceManager.getDataSource().entrySet()) {
            PlatformTransactionManager transactionManager = this.createTransactionManager((DataSource) entry.getValue());
            transactionManagers.add(transactionManager);
        }
        Collections.reverse(transactionManagers);
    }


    protected PlatformTransactionManager createTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    public List<PlatformTransactionManager> getTransactionManagers() {
        return transactionManagers;
    }

    public MultiDataSourceManager getMultiDataSourceManager() {
        return multiDataSourceManager;
    }

    public void setMultiDataSourceManager(MultiDataSourceManager multiDataSourceManager) {
        this.multiDataSourceManager = multiDataSourceManager;
    }
}
