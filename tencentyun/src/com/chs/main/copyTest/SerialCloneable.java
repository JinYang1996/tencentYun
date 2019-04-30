package com.chs.main.copyTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerialCloneable implements Cloneable,Serializable{
	
	private static final Long serialVersionUID = 1L;
	
	public Object clone(){
		try{
			//1.�Ƚ�����תΪ��
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(this);
			out.close();
			
			//2.����д���ڴ�
			ByteArrayInputStream in = new ByteArrayInputStream(bout.toByteArray());
			ObjectInputStream oin = new ObjectInputStream(in);
			Object res = oin.readObject();
			oin.close();
			return res;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
