package com.chs.main.option;

public class FastOrder {

	public static void order(int[] a,int start,int end){
		int i,j,index;
		if(start > end){
			return;
		}
		i = start;
		j = end;
		index = a[i];
		while(i < j){
			while(i < j && a[j] >= index)
				j--;
			if(i < j)
				a[i] = a[j];
			while(i < j && a[i] < index)
				i++;
			if(i < j)
				a[j] = a[i];
		}
		a[i] = index;
		order(a,start,i-1);
		order(a,i+1,end);
	}
}
