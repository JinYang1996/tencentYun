package com.chs.main.option;

public class Binary {

	public static int binary(int[] a , int start,int end,int target){
		int mid = (start + end)/2;
		if(start > end){
			return -1;
		}
		if(target == a[mid]){
			return mid;
		}else if(target < a[mid]){//С���е�λ�� �ݹ��Ҳ�
			return binary(a,start,mid-1,target);
		}else{
			return binary(a,mid+1,end,target);
		}
	}
}
