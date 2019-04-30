package com.chs.main.copyTest;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class Client {

	public static void main(String[] args){
		Person person1 = new Person();
		Person person2 = new Person();
		
		Car car = new Car();
		car.setName("°ÂÍÐ");
		car.setType("À¬»ø³µ");
		person1.setCar(car);
		person1.setName("¼ñÀ¬»ø´ÎµÄ");
		
		person2 = (Person) person1.clone();
		
		System.out.println(person1.toString());
		System.out.println(person2.toString());
		System.out.println(person1.equals(person2));
		
		Person person3 = new Person();
		try {
			BeanUtils.copyProperties(person3, person1);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(person3.toString());
		System.out.println(person1.equals(person3));
	}
}
