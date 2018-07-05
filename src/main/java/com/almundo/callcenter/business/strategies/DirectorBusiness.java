package com.almundo.callcenter.business.strategies;

import com.almundo.callcenter.entities.ClientRequest;
import com.almundo.callcenter.entities.Employee;
import com.almundo.callcenter.exceptions.CallException;

public class DirectorBusiness extends EmployeeBusiness {

	public DirectorBusiness(String threadId, Employee employee, ClientRequest clientRequest) {
		super(threadId, employee, clientRequest);
	}

	@Override
	protected void answerCall() throws CallException {		
		log.info("El cliente {} es atendido por el empleado {}, que es un {}", this.clientRequest.getClient().getName(), 
				this.employee.getName(), this.employee.getTypeEmployee().getName());
		
		// Lance una excepcion solo para cambiar el codigo dentro de este metodo. 
		// No obstante, se puede cambiar el codigo para que espere el tiempo X de llamada (ver en otra clase del tipo EmployeeBusiness).
		throw new CallException("Termino la llamada porque soy el Director y no tengo ganas de atender a nadie");		
	}

}
