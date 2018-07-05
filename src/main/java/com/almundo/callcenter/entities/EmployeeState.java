package com.almundo.callcenter.entities;

public enum EmployeeState {
	BUSY("Busy"),
	AVAILABLE("Available");
	
	private String state;
	
	private EmployeeState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return this.state;
	}
}
