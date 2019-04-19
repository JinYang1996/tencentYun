package com.chs.main.copyTest1;

public class Client {

	public static void main(String[] args){
		Bus bus = new Bus();
		bus.setName("公交车1");
		bus.setType("bus");
		
		Person person = new Person();
		person.setName("张三");
		person.setAge("12");
		person.setBus(bus);
		
		Person person1 = (Person) person.clone();
		System.out.println(person);
		System.out.println(person1);
		System.out.println(person.equals(person1));
		System.out.println(0.1*3==0.3);
		System.out.println(0.1*3);
		System.out.println(0.1*4);
		System.out.println(0.1*2);
	}
}
