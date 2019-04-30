package com.chs.main.proxy4;

public class RealSendMessage implements SendMessage{

	@Override
	public void send(){
		System.out.println("sendMessage is use");
	}
}
