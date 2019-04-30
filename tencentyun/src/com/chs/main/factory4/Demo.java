package com.chs.main.factory4;

public class Demo {

	public static void main(String[] args){
		Option add = EasyFactory.getAdd();
		Option sub = EasyFactory.getSub();
		System.out.println(add.getResult(3, 5));
		System.out.println(sub.getResult(3, 5));
	}
	
	
}
