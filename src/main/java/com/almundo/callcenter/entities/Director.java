package com.almundo.callcenter.entities;

public class Director extends Employee {

	private static final long serialVersionUID = -3742723946101264298L;

	public Director(String employeeCode, EmployeeState state) {
		super(employeeCode, TypeEmployee.DIRECTOR, state);
	}
	

	public Director(String employeeCode, String name, int dni, EmployeeState state) {
		super(employeeCode, name, dni, TypeEmployee.DIRECTOR, state);
	}	
}
