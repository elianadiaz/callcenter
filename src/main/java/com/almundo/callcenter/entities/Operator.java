package com.almundo.callcenter.entities;

public class Operator extends Employee {
	
	private static final long serialVersionUID = 5901392182694933138L;

	public Operator(String employeeCode, EmployeeState state) {
		super(employeeCode, TypeEmployee.OPERATOR, state);
	}
	
	public Operator(String employeeCode, String name, int dni, EmployeeState state) {
		super(employeeCode, name, dni, TypeEmployee.OPERATOR, state);
	}
}
