package com.chs.main.factory;

public class Add implements Option{

	@Override
	public double getResult(double numberA,double numberB){
		return numberA+numberB;
	}
}
