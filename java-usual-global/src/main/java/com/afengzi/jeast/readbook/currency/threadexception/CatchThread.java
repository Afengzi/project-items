package com.afengzi.jeast.readbook.currency.threadexception;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 14-8-29
 * Time: ÉÏÎç9:23
 * To change this template use File | Settings | File Templates.
 */
public class CatchThread {


    public static class exceptionThread implements Runnable{

        @Override
        public void run() {
            Thread thread = Thread.currentThread() ;
            System.out.println("run by "+thread.getName());
            System.out.println("eh0 "+thread.getUncaughtExceptionHandler());
            throw new RuntimeException(" throws exception ....");

        }
    }

    public static class MyUnCaughtException implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("uncaughtException throwable : "+e);
            System.out.println("uncaughtException Thread "+t.getName());
        }
    }
}
