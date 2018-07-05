package com.almundo.callcenter.entities;

import java.io.Serializable;
import java.util.Observable;

public abstract class Employee extends Observable implements Serializable, Comparable<Employee> {

	private static final long serialVersionUID = -7685667313839352939L;

	private String employeeCode;
	private String name;
	private int dni;
	private TypeEmployee typeEmployee;
	private EmployeeState state;
	
	public Employee(String employeeCode, String name, int dni, TypeEmployee typeEmployee, EmployeeState state) {
		super();
		this.employeeCode = employeeCode;
		this.name = name;
		this.dni = dni;
		this.typeEmployee = typeEmployee;
		this.state = state;
	}
	
	public Employee(String employeeCode, TypeEmployee typeEmployee, EmployeeState state) {
		super();
		this.typeEmployee = typeEmployee;
		this.state = state;
		this.employeeCode = employeeCode;
		this.name = "";
		this.dni = 0;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public TypeEmployee getTypeEmployee() {
		return typeEmployee;
	}

	public void setTypeEmployee(TypeEmployee typeEmployee) {
		this.typeEmployee = typeEmployee;
	}

	public EmployeeState getState() {
		return state;
	}

	public void setState(EmployeeState state) {
		this.state = state;
		
		//Marcamos el objeto observable como objeto que ha cambiado
        setChanged();
        //Notificamos a los observadores y le enviamos el nuevo valor
        notifyObservers(this.state);
        //notifyObservers(); Este metodo solo notifica que hubo cambios en el objeto
	}
	
	@Override
	public int compareTo(Employee otherEmployee) {
		if (this.getTypeEmployee().getAttention() < otherEmployee.getTypeEmployee().getAttention()) {
			return 1;
		} else if (this.getTypeEmployee().getAttention() > otherEmployee.getTypeEmployee().getAttention()) {
			return -1;
		}
		
		return compareRestOfAttributes(otherEmployee);		
	}
	
	private int compareRestOfAttributes(Employee otherEmployee) {
		if (EmployeeState.AVAILABLE.equals(this.getState()) && EmployeeState.BUSY.equals(otherEmployee.getState())) {
			return 1;
		} else if (EmployeeState.BUSY.equals(this.getState()) && EmployeeState.AVAILABLE.equals(otherEmployee.getState())) {
			return -1;
		}
		
		// Tienen el mismo estado
		int rdo = this.getEmployeeCode().compareTo(otherEmployee.getEmployeeCode());
		if (rdo == 0) {
			rdo = this.getDni() - otherEmployee.getDni();
			
			if (rdo == 0) {
				return 0;
			} else if (rdo > 0) {
				return 1;
			} else {
				return -1;
			}
		}
		
		return rdo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dni;
		result = prime * result + ((employeeCode == null) ? 0 : employeeCode.hashCode());
		result = prime * result + ((typeEmployee == null) ? 0 : typeEmployee.getAttention());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (dni != other.dni)
			return false;
		if (employeeCode == null) {
			if (other.employeeCode != null)
				return false;
		} else if (!employeeCode.equals(other.employeeCode))
			return false;
		return true;
	}	
}
