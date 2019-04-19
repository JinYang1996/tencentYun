package com.chs.main.factory1;

public class Client {

	public static void main(String[] args) throws Exception{
		Option add = EasyFactory.getOption("+");
		Option sub = EasyFactory.getOption("-");
		System.out.println(add.getResult(5, 2));
		System.out.println(sub.getResult(5, 2));
	}
}
