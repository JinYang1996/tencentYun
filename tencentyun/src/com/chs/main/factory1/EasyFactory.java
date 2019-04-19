package com.chs.main.factory1;

public class EasyFactory {
	
	public static Option getOption(String type){
		Option option = null;
		switch(type){
		case "+":
			option = new Add();
			break;
		case "-":
			option = new Sub();
			break;
		default:
			System.out.println("无此方法");
			break;
		}
		return option;
	}
}
