package com.chs.main.factory;

public class EasyFactory {

	public static Option add(){
		System.out.println("这是加法");
		return new Add();
	}
	
	public static Option sub(){
		System.out.println("这是减法");
		return new Sub();
	}
	
	public static Option createFactory(String name){
		Option option = null;
		switch(name){
			case "+":
				option = add();
				break;
			case "-":
				option = sub();
				break;
		}
		return option;
	}
}
