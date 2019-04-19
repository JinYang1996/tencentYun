package com.chs.main.thread;

public class Ticket extends Thread{

	public Ticket(String name){
		super(name);
	}
	
	private static int tickets = 100;
	
	static Object lock = "key";
	
	public static void subTicket(){
		synchronized(lock){
			tickets --;
		}
	}
	
	public void run(){
		while(true){
			synchronized(lock){
				if(tickets > 0){
					subTicket();
					System.out.println("当前线程："+Thread.currentThread().getName()+" ticket="+tickets);
				}
			}
			
		}
	}
}
