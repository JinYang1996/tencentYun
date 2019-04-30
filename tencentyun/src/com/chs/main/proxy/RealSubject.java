package com.chs.main.proxy;

public class RealSubject implements Subject{

	@Override
	public void doSomething(){
		System.out.println("l'm doing something");
	}
}
