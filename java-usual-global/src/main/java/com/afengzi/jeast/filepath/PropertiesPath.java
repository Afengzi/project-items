package com.afengzi.jeast.filepath;

import java.io.InputStream;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 14-7-14
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesPath {

    public static void main(String[] args) {
        String url1 = PropertiesPath.class.getResource("").toString();
        String url2 = PropertiesPath.class.getResource("/").toString();
//        String url3 = PropertiesPath.class.getClassLoader().getResource("/").toString() ;
        String url4 = PropertiesPath.class.getClassLoader().getResource("").toString() ;
        System.out.println(url1);
        System.out.println(url2);
//        System.out.println(url3);
        System.out.println(url4);

        readByRel();

        readByAbs();
    }

    private static void readByRel(){
        InputStream inputStream = PropertiesPath.class.getResourceAsStream("pro.properties") ;
        System.out.println(inputStream);
    }

    private static void readByAbs(){
        InputStream inputStream = PropertiesPath.class.getClassLoader().getResourceAsStream("\\src\\com\\afengzi\\jeast\\filepath\\pro.properties");
        System.out.println(inputStream);
    }
}
