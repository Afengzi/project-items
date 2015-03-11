package com.afengzi.jeast.video.current.abstract001;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 14-9-10
 * Time: 下午6:32
 * To change this template use File | Settings | File Templates.
 */
public class ThreadMain {

    public static void main(String[] args ){
        //extends Thread方式
        ExtendsThread extendsThread = new ExtendsThread();
        extendsThread.start();
        //implements Runnable方式
        ImplementsRunable implementsRunable = new ImplementsRunable();
        Thread implementsT = new Thread(implementsRunable);
        implementsT.start();
    }

    /**静态内部类可以使外部类不需要拥有对内部类的引用，方便GC.*/
    public static class ExtendsThread extends Thread{
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                //TODO do something...
            }
        }
    }

    public static class ImplementsRunable implements Runnable{

        @Override
        public void run() {
            //TODO do something...
        }
    }
}
