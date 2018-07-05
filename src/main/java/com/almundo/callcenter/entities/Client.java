package com.almundo.callcenter.entities;

import java.io.Serializable;

public class Client implements Serializable {
	
	private static final long serialVersionUID = -56691055566041665L;

	private String clientCode;
	private String name;
	private Integer dni;
	
	public Client() {
		super();
	}
	
	public Client(String clientCode, String name, Integer dni) {
		super();
		this.clientCode = clientCode;
		this.name = name;
		this.dni = dni;
	}
	
	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
