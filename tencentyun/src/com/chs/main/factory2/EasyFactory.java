package com.chs.main.factory2;

public class EasyFactory {
	
	public static Option getOption(String type) throws Throwable{
		Option option = null;
		switch(type){
			case "+":
				option = new Add();
				break;
			case "-":
				option = new Sub();
				break;
			default:
		}
		return option;
	}
}
