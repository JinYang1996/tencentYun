package com.chs.main.factory3;

public class EasyFactory {
	public Option getOption(String name){
		Option option = null;
		switch(name){
		case "+":
			option = new Add();
			break;
		case "-":
			option = new Sub();
			break;
		}
		return option;
	}
}
enum color{
	GREEN,RED,YELLOW
}
