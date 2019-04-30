package com.chs.main.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {

	public static void main(String[] args){
		Timer t = new Timer();
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);
		
		t.schedule(new TimerTask(){
			@Override
			public void run(){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(new Date());
				System.out.println("定时任务Zzz,当前时间为："+date);
			}
		},calendar.getTime(),2000);
	}
}
