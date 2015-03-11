package com.afengzi.jeast.readbook.currency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <title>AtomicOperator</title>
 *
 * <project>java-essay</project>
 *
 * <package>com.afengzi.jeast.readbook.currency</package>
 *
 * <file>AtomicOperator.java</file>
 *
 * <date>2014年6月4日</date>
 *
 * <brief>原子操作</brief>
 *
 * @author klov
 *
 */
public class AtomicOperator implements Runnable{
	
	private AtomicLong l = new AtomicLong();
	
	public long getValu(){
		return l.get() ;
	}
	public void evenInrement(){
		System.out.println(Thread.currentThread().getId()+"  threadId");
		l.addAndGet(2);
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			evenInrement();
		}
		
	}
	
	private void exit(){
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("interupt...");
				System.exit(0);
			}
		}, 5000);
	}
	public static void main(String[] args) {
		AtomicOperator ao = new AtomicOperator();
		ao.exit();
		
//		ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService executor = Executors.newFixedThreadPool(5);
		for(int i = 0 ; i < 5 ; i++){
			executor.execute(new AtomicOperator());
			
		}
		while(true){
			long l = ao.getValu();
			if(l%2!=0){
				System.out.println("thread bad ."+l);
			}
		}
		
	}

}














