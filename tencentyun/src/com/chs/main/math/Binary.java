package com.chs.main.math;

public class Binary {

	public static int binary(int[] arr,int left,int right,int target){
		int mid = (left+right)/2;
		if(mid > arr[right] || mid < arr[left] || left>right){
			return -1;
		}
		if(arr[mid] == target){
			return mid;
		}else if(arr[mid] > target){
			return binary(arr,left,mid-1,target);
		}else{
			return binary(arr,mid+1,right,target);
		}
	}
}
