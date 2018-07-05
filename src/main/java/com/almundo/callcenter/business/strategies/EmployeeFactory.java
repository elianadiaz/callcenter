package com.almundo.callcenter.business.strategies;

import com.almundo.callcenter.entities.ClientRequest;
import com.almundo.callcenter.entities.Employee;
import com.almundo.callcenter.entities.TypeEmployee;

public class EmployeeFactory {

	public static EmployeeBusiness createEmployeeBusiness(String threadId, Employee employee, ClientRequest clientRequest) {
		if (employee == null || employee.getTypeEmployee() == null 
				|| clientRequest == null || clientRequest.getClient() == null) {
			return new NullEmployeeBusiness(threadId);
		}
		
		if (TypeEmployee.DIRECTOR.equals(employee.getTypeEmployee())) {
			return new DirectorBusiness(threadId, employee, clientRequest);
		} else if (TypeEmployee.OPERATOR.equals(employee.getTypeEmployee())) {
			return new OperatorBusiness(threadId, employee, clientRequest);
		} else if (TypeEmployee.SUPERVISOR.equals(employee.getTypeEmployee())) {
			return new SupervisorBusiness(threadId, employee, clientRequest);
		}
		
		return new NullEmployeeBusiness(threadId);
	}
}
