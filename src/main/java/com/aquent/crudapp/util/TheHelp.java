package com.aquent.crudapp.util;

public class TheHelp {
	
	public static String getFullname(String first, String last){
	
		if(first != null && last != null){
			return first + " " + last;
		}
		return null;
	}

}
