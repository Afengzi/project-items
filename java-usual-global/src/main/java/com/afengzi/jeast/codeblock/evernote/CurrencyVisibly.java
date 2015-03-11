package com.afengzi.jeast.codeblock.evernote;


public class CurrencyVisibly {

	private static boolean read ;
	private static long number ;
	
	private static class ReadThread extends Thread{

		@Override
		public void run() {
			System.out.println("run start...");
			while(!read){
				System.out.println("while start...");
				Thread.yield();
				System.out.println("while end...");
			}
			System.out.println("run end...");
			System.out.println(number);
		}
		
	}
	public static void main(String[] args) {
		System.out.println("main 1...");
		new ReadThread().start();
		System.out.println("main 2...");
		/*try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		number=42;
		read=true;
	}
}
