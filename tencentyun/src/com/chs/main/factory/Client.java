package com.chs.main.factory;

public class Client {

	public static void main(String[] args) throws Exception{
		EasyFactory factory = new EasyFactory();
		Option add = factory.createFactory("+");
		Option sub = factory.createFactory("-");
		
		System.out.println(add.getResult(3, 5));
		System.out.println(sub.getResult(3, 5));
	}
}
