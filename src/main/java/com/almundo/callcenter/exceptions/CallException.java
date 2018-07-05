package com.almundo.callcenter.exceptions;

public class CallException extends Exception {

	private static final long serialVersionUID = 4195424227353983987L;

	public CallException() {
		super("There was a problem during the attention of the call.");
	}
	
	public CallException(String msg) {
		super(msg);
	}	
}
