package com.afengzi.jeast.mths.arithmetic;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <title>HowManyHorse</title>
 *
 * <project>java-essay</project>
 *
 * <package>com.afengzi.jeast.mths.arithmetic</package>
 *
 * <file>HowManyHorse.java</file>
 *
 * <date>2014年6月3日</date>
 *
 * <brief></brief>
 *
 * @author klov
 *
 */
public class HowManyHorse {

	/**
	 * 100匹马托100袋粮食,大马每匹驼两袋，小马每匹驼一袋，小马仔2匹驼1袋。求大马、小马、小马子各多少匹
	 */
	private static void essay1239() {

		int answor = 1;

		for (int babyHorse = 0; babyHorse < 101; babyHorse = babyHorse + 2) {
			for (int bigHorse = 0; bigHorse < 101; bigHorse++) {
				for (int littleHorse = 0; littleHorse < 101; littleHorse++) {
					if ((bigHorse + littleHorse + babyHorse == 100)
							&& (bigHorse * 2 + littleHorse + babyHorse / 2 == 100)) {
						System.out.println("***answer " + answor
								+ "***********split line**********************");
						System.out.println("the bigHorse is : " + bigHorse);
						System.out.println("the littleHorse is : " + littleHorse);
						System.out.println("the babyHorse is : " + babyHorse);
						answor++;
					}
				}
			}

		}

	}

	/**
	 * 一个数等于所有因数之和则成为完数，如： 6=1+2+3 求 10000以内的所有完数 at 2014-04-05 18:08:17 the 1
	 * wanshu : 0****** the 2 wanshu : 6****** the 3 wanshu : 28****** the 4
	 * wanshu : 496******
	 */
	private static void essay20140405() {

		int index = 1;
		for (int i = 0; i < 1001; i++) {
			int sum = 0;
			for (int j = 1, max = i / 2 + 1; j < max; j++) {
				if (i % j == 0) {
					sum += j;
				}
			}
			if (i == sum) {
				System.out.println("*****the " + index++ + " wanshu : " + i + "******");
			}

		}
	}

	/***********************************
	 * 计算双数 121、1221 at 2014-04-05 18:23:50
	 */
	private static void essay1817() {
		int index = 0;
		for (int i = 10; i < 100001; i++) {
			if (calculateDoubleData(i)) {
				System.out.println("*****index " + index++ + "***** " + i + "*****");
			}
		}

	}

	private static boolean calculateDoubleData(int num) {
		String numStr = String.valueOf(num);
		for (int i = 0, len = numStr.length() / 2; i < len; i++) {
			if (numStr.charAt(i) != numStr.charAt(numStr.length() - i - 1)) {
				return false;
			}
		}
		return true;
	}

	/*************************************/
	/*************************************
	 * 求素数：除了1和它本身外没有其他因数的数称为素数 at 2014-04-05 18:52:38
	 */
	private static void essay1849() {
		int index = 1;
		for (int i = 3; i < 30000; i++) {
			if (isPrime(i)) {
				System.out.println("********the index " + index++ + " prime number is " + i
						+ "*****");
			}
		}
	}

	private static boolean isPrime(int num) {
		for (int j = 2, max = num / 2 + 1; j < max; j++) {
			if (num % j == 0) {
				return false;
			}
		}
		return true;
	}

	/*******************************************/

	private static void essay1933() {
		int[] rabbits = { 1, 1 };
		for (int i = 3; i <= 5; i = i + 2) {
			rabbits[0] = rabbits[0] + rabbits[1];
			rabbits[1] = rabbits[0] + rabbits[1];
		}
		System.out.println("******the twentyth month rabbits is " + rabbits[0]);
	}

	private static void essay1939() {
		System.out.println(calculateRabbit(5));
	}

	private static int calculateRabbit(int month) {
		if (month == 1 || month == 2) {
			return 1;
		}
		return calculateRabbit(month - 1) + calculateRabbit(month - 2);
	}

	private static void calculateRuntime() {
		long start = System.currentTimeMillis();
		essay1933();
		System.out.println("*******耗时：" + (System.currentTimeMillis() - start));
	}

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(new Date()));
		// essay1239();
		// essay1939();
		essay1849();
		// calculateDoubleData(1000);
	}
}
