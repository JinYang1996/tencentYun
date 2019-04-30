package com.chs.main.proxy2;

public class SendMessageReal implements SendMessage{

	@Override
	public void send(){
		System.out.println("sendMessage is doing");
	}
}
