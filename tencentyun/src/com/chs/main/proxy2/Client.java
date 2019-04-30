package com.chs.main.proxy2;

import java.lang.reflect.Proxy;

public class Client {

	public static void main(String[] args){
		/*SendMessage target = new SendMessageReal();
		JDKDynamicProxy proxy = new JDKDynamicProxy(target);
		SendMessage sendMessage = (SendMessage)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), proxy);
		sendMessage.send();*/
		SendMessage sendMessage = new JDKDynamicProxy(new SendMessageReal()).getProxy();
		sendMessage.send();
	}
}
