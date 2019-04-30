package com.chs.main.copyTest1;

public class Bus extends SerialCloneable{

	private static final long serialVersionUID = 1L;
	public String name;
	public String type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Bus [name=" + name + ", type=" + type + "]";
	}
	
}
