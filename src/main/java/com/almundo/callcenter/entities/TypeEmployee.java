package com.almundo.callcenter.entities;

public enum TypeEmployee {
	OPERATOR("Operator", 3),
	SUPERVISOR("Supervisor", 2),
	DIRECTOR("Director", 1);
	
	private String name;
	private int attention;
	
	private TypeEmployee(String name, int attention) {
		this.name = name;
		this.attention = attention;
	}

	public String getName() {
		return this.name;
	}

	public int getAttention() {
		return this.attention;
	}
}
