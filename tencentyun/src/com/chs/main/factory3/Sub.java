package com.chs.main.factory3;

public class Sub implements Option{

	@Override
	public double getResult(double numA,double numB){
		return numA-numB;
	}
}
