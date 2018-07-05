package com.almundo.callcenter.entities;

public class Supervisor extends Employee {

	private static final long serialVersionUID = 7777048666289678853L;
	
	public Supervisor(String employeeCode, String name, int dni, EmployeeState state) {
		super(employeeCode, name, dni, TypeEmployee.SUPERVISOR, state);
	}

	public Supervisor(String employeeCode, EmployeeState state) {
		super(employeeCode, TypeEmployee.SUPERVISOR, state);
	}

}
