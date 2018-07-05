package com.almundo.callcenter.exceptions;

public class StateException extends Exception {

	private static final long serialVersionUID = 4195424227353983987L;

	public StateException() {
		super("Incorrect status.");
	}
	
	public StateException(String msg) {
		super(msg);
	}	
}
