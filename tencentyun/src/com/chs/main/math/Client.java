package com.chs.main.math;

public class Client {

	public static void main(String[] args){
		int[] a = {1,5,3,99,4,78,23,41,15,29};
		Bobbing.bobbing(a);
		for(int s : a){
			System.out.println(s);
		}
		System.out.println(Binary.binary(a, 0, a.length-1, 3));
	}
}
