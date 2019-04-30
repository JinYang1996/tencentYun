package com.chs.main.factory;

public class Sub implements Option{

	@Override
	public double getResult(double numberA,double numberB){
		return numberA-numberB;
	}
}
