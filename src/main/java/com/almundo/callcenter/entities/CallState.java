package com.almundo.callcenter.entities;

public enum CallState {
	IN_PROCESS("In process"),
	FINISHED("Finished");
	
	private String state;
	
	private CallState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return this.state;
	}
}
