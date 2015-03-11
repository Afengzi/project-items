package com.afengzi.jeast.codeblock.evernote;

import java.util.Vector;

/**
 * <title>CurrencyBasis</title>
 *
 * <project>java-essay</project>
 *
 * <package>com.afengzi.jeast.codeblock.evernote</package>
 *
 * <file>CurrencyBasis.java</file>
 *
 * <date>2014年6月1日</date>
 *
 * <brief>evernote夯实地基</brief>
 *
 * @author klov
 *
 */
public class CurrencySafety {

	private long coun ;
	private Vector<String> v = new Vector<String>();
	
	public void addElement(String e){
		if(!v.contains(e)){
			v.add(e);
		}
	}
}
