package com.chs.main.option;

public class Client {

	public static void main(String[] args){
		int[] a = {1,3,5,23,20,324,33,234,54,21,43,122,32,334};
		/*FastOrder.order(a, 0, a.length-1);*/
		Bobbing.bobbing(a);
		for(int i=0;i<a.length;i++){
			System.out.println(a[i]);
		}
		int[] b = {1,2,3,4,5,6,7,8,9};
		System.out.println(Binary.binary(b, 0, b.length-1,5));
		
	}
}
