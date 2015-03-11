package com.afengzi.jeast.util.system;

import java.util.Properties;

import org.junit.Test;

/**
 * @author klov date:20140601 time:0027
 * 
 */
public class SystemUtil {

	public static String getSystemBit() {
		return getSystemProperties().getProperty("sun.arch.data.model");
	}

	public static Properties getSystemProperties() {
		Properties pro = System.getProperties();
		return pro;
	}

	@Test
	public void testGetSystemBit() {
		System.out.println(getSystemProperties().getProperty(
				"sun.arch.data.model"));
	}
}
