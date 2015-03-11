package com.afengzi.jeast.util.bean2dbobject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.afengzi.jeast.util.bean2dbobject.bean.Person;

/**
 * <title>Bean2MapUtil</title>
 *
 * <project>java-essay</project>
 *
 * <package>com.afengzi.jeast.util.bean2dbobject</package>
 *
 * <file>Bean2MapUtil.java</file>
 *
 * <date>2014年6月10日</date>
 *
 * <brief></brief>
 *
 * @author klov
 *
 * @param <T>
 */
public class Bean2MapUtil<T> {

	public Map<String,T> convertBean2Map(Object obj){
		
		Map<String,T> result = new HashMap<String,T>();
		if(obj==null){
			return result ;
		}
		Class<? extends Object> clazz = obj.getClass();
		if(clazz.getDeclaredFields().length==0){
			return result ;
		}
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			if(field==null){
				continue;//一般情况下不会是null
			}
			field.setAccessible(true);//设置private属性可访问
			String type = field.getType().getName();
			System.out.println(type);
			String key = field.getName();
			try {
			@SuppressWarnings("unchecked")
			T value = (T) field.get(obj);
			result.put(key, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result ;
	}
	public static void main(String[] args) {
		Person person = new Person();
		person.setAge(23);
		person.setName("klov");
		person.setSex("man");
		
		Bean2MapUtil<String> util = new Bean2MapUtil<String>();
		System.out.println(util.convertBean2Map(person));
	}

}
