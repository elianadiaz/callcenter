package com.almundo.callcenter.repositories;

import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Repository;

import com.almundo.callcenter.entities.Director;
import com.almundo.callcenter.entities.Employee;
import com.almundo.callcenter.entities.EmployeeState;
import com.almundo.callcenter.entities.Operator;
import com.almundo.callcenter.entities.Supervisor;

@Repository
public class EmployeeRepository implements IEmployeeRepository {

	@Override
	public SortedSet<Employee> getAvailableEmployees() {
		// Voy a mockear los empleados disponibles.		
		SortedSet<Employee> employees = new TreeSet<Employee>();
		
		employees.add(new Director("dir-1", "Primer director", 28896111, EmployeeState.AVAILABLE));
		employees.add(new Operator("oper-1", "Primer operador", 20096111, EmployeeState.AVAILABLE));
		employees.add(new Supervisor("sup-1", "Primer supervisor", 15096111, EmployeeState.AVAILABLE));
		
		employees.add(new Director("dir-2", "Segundo director", 38896111, EmployeeState.AVAILABLE));
		employees.add(new Operator("oper-2", "Segundo operador", 30096111, EmployeeState.AVAILABLE));
		employees.add(new Supervisor("sup-2", "Segundo supervisor", 35096111, EmployeeState.AVAILABLE));
		
		employees.add(new Operator("oper-3", "Tercer operador", 33096111, EmployeeState.AVAILABLE));
		employees.add(new Operator("oper-4", "Cuarto operador", 34096111, EmployeeState.AVAILABLE));
		employees.add(new Supervisor("sup-3", "Tercer supervisor", 33336111, EmployeeState.AVAILABLE));
		
		employees.add(new Operator("oper-5", "Quinto operador", 35555111, EmployeeState.AVAILABLE));		
		
		return employees;
	}

}
