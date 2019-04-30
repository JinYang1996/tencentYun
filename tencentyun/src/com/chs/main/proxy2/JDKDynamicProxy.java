package com.chs.main.proxy2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicProxy implements InvocationHandler{

	private Object target;
	
	public JDKDynamicProxy(Object target){
		this.target = target;
	}
	
	public <T>T getProxy(){
		return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
	}
	
	@Override
	public Object invoke(Object proxy,Method method,Object[] args) throws Throwable{
		System.out.println("before sendMessage,I need intercept you");
		Object result = method.invoke(target, args);
		System.out.println("ok,you can get through");
		return result;
	}
}
