package com.chs.main.proxy3;

public class RealSendMessage implements SendMessage{

	public void send(){
		System.out.println("sendMessage to user...");
	}
}
