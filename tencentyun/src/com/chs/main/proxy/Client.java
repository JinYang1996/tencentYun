package com.chs.main.proxy;

public class Client {

	public static void main(String[] args){
		//jdk¶¯Ì¬´úÀí²âÊÔ
		Subject subject = new JDKDynamicProxy(new RealSubject()).getProxy();
		subject.doSomething();
	}
}
