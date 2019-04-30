package com.chs.main.proxy1;

public class RealSubject implements Subject{

	@Override
	public void doSomething(){
		System.out.println("Subject doing something");
	}
}
