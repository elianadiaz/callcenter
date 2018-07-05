package com.almundo.callcenter.entities;

public enum ClientRequestState {
	ON_HOLD("On hold"),
	ATTENDED("Attended"),
	IN_ATENTION("In atention"),
	CANCELLED("Cancelled");
	
	private String state;
	
	private ClientRequestState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return this.state;
	}
}
