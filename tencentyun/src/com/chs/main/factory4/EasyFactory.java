package com.chs.main.factory4;

public class EasyFactory {

	public static Option getAdd(){
		return new Add();
	}
	
	public static Option getSub(){
		return new Sub();
	}
	
}
