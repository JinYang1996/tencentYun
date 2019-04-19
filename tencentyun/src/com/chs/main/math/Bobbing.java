package com.chs.main.math;

public class Bobbing {

	public static void bobbing(int[] array){
		for(int i=0;i<array.length;i++){
			for(int j=1;j<array.length-1-i;j++){
				int temp ;
				if(array[j] > array[j+1]){
					temp = array[j+1];
					array[j+1] = array[j];
					array[j] = temp;
				}
			}
		}
	}
}
