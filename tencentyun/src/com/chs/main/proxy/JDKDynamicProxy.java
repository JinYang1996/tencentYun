package com.chs.main.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicProxy implements InvocationHandler{

	private Object target;
	
	public JDKDynamicProxy(Object target){
		this.target = target;
	}
	
	/**
	 * 获取被代理实例对象
	 * @return
	 */
	public <T>T getProxy(){
		return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
	
	@Override
	public Object invoke(Object proxy,Method method,Object[] args) throws Throwable{
		System.out.println("doSomething before");
		Object result = method.invoke(target, args);
		System.out.println("doSomething end");
		return result;
	}
}
