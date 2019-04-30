package com.chs.main.thread;

public class Client {

	public static void main(String[] args){
		Ticket ticket1 = new Ticket("用户A");
		Ticket ticket2 = new Ticket("用户B");
		ticket1.start();
		ticket2.start();
	}
}
