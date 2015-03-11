package com.afengzi.shardingdatabase.onlydb.onlytransaction;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 15-2-10
 * Time: обнГ1:20
 * To change this template use File | Settings | File Templates.
 */
public class DataSourceIdentificationHolder {
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    public static String getIdentification() {
        return holder.get();
    }

    public static void setIdentification(String identification) {
        holder.set(identification);
    }

    public static void removeIdentification() {
        holder.remove();
    }
}
