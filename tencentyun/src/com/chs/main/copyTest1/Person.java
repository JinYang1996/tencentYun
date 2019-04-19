package com.chs.main.copyTest1;

public class Person extends SerialCloneable{

	private static final long serialVersionUID = 1L;
	public String name;
	public String age;
	public Bus bus;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Bus getBus() {
		return bus;
	}
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", bus=" + bus + "]";
	}
	
}
