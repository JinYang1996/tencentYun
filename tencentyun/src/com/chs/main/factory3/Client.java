package com.chs.main.factory3;

public class Client {

	public static void main(String[] args){
		EasyFactory factory = new EasyFactory();
		Option add = factory.getOption("+");
		Option sub = factory.getOption("-");
		
		System.out.println(add.getResult(3,4));
		System.out.println(sub.getResult(3,4));
	}
}
