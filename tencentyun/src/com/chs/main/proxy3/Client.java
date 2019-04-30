package com.chs.main.proxy3;

import java.lang.reflect.Proxy;

public class Client {

	public static void main(String[] args){
		/*SendMessage sendMessage = new RealSendMessage();
		JDKDynamicProxy proxy = new JDKDynamicProxy(sendMessage);
		
		SendMessage send = (SendMessage)Proxy.newProxyInstance(sendMessage.getClass().getClassLoader(),sendMessage.getClass().getInterfaces(),proxy);
		send.send();*/
		SendMessage send = new JDKDynamicProxy(new RealSendMessage()).getProxy();
		send.send();
	}
}
