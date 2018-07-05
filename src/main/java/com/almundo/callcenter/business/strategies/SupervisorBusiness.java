package com.almundo.callcenter.business.strategies;

import com.almundo.callcenter.entities.ClientRequest;
import com.almundo.callcenter.entities.Employee;
import com.almundo.callcenter.exceptions.CallException;

public class SupervisorBusiness extends EmployeeBusiness {

	public SupervisorBusiness(String threadId, Employee employee, ClientRequest clientRequest) {
		super(threadId, employee, clientRequest);
	}

	@Override
	protected void answerCall() throws CallException {
		log.info("El cliente {} es atendido por el empleado {}, que es un {}", this.clientRequest.getClient().getName(), 
				this.employee.getName(), this.employee.getTypeEmployee().getName());
		
		long attentionTimeInMilliseconds = getAttentionTimeInMilliseconds();
		
		try {
			Thread.sleep(attentionTimeInMilliseconds);
		} catch (InterruptedException e) {			
			log.warn("Ocurrio un error durante el tiempo ({} milisegundos) asignado a la llamada: {}", 
					attentionTimeInMilliseconds, e.getMessage());			
			
			throw new CallException();
		}
	}

}
