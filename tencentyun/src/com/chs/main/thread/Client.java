package com.chs.main.thread;

public class Client {

	public static void main(String[] args){
		Ticket ticket1 = new Ticket("�û�A");
		Ticket ticket2 = new Ticket("�û�B");
		ticket1.start();
		ticket2.start();
	}
}
