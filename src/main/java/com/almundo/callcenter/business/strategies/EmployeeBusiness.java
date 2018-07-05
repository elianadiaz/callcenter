package com.almundo.callcenter.business.strategies;

import java.util.Observable;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.callcenter.entities.CallState;
import com.almundo.callcenter.entities.ClientRequest;
import com.almundo.callcenter.entities.Employee;
import com.almundo.callcenter.entities.ClientRequestState;
import com.almundo.callcenter.entities.EmployeeState;
import com.almundo.callcenter.exceptions.CallException;
import com.almundo.callcenter.exceptions.StateException;

public abstract class EmployeeBusiness extends Observable implements Runnable {

	protected static final Logger log = LoggerFactory.getLogger(EmployeeBusiness.class);
	
	private static final int MINIMUM_TIME_CALL_SECONDS = 5;
	private static final int DIFFERENCE_TIME_MAXIMUM_AND_MINIMUM_CALL_SECONDS = 5; // 10 - 5 = 5
	
	protected Employee employee;
	protected ClientRequest clientRequest;
	private Random random;
	private String threadId;
	
	public EmployeeBusiness(String threadId, Employee employee, ClientRequest clientRequest) {
		super();
		this.employee = employee;
		this.clientRequest = clientRequest;
		this.random = new Random();
		this.threadId = threadId;
	}

	@Override
	public void run() {
		if (this.employee == null || this.clientRequest == null || this.clientRequest.getClient() == null) {
			log.warn("No se ha podido iniciar la atencion de la llamada, porque el cliente o el empleado no fueron asignados.");
		}
		
		String clientName = (this.clientRequest.getClient().getName() != null ? this.clientRequest.getClient().getName() : "");
		
		try {
			log.info("Comienza a atenderse la llamada con el cliente {}", clientName);

			changeStatusOfParticipantsInitially();
			
			boolean finishedSuccessfully = true;
			try {
				answerCall();
			} catch (CallException ex) {
				log.warn("Hubo un problema durante la llamada: {}", ex.getMessage());
				
				finishedSuccessfully = false;
			}
			
			if (finishedSuccessfully) {
				changeStatusOfParticipantsInClosureSuccessfully();
			} else {
				changeStatusOfParticipantsInClosureWithError();
			}		
		} catch (StateException e) {
			log.warn("Hubo un error los estado del cliente o del empleado asignados en la llamada: {}", e.getMessage());			
		} catch (Exception e) {
			log.warn("Hubo un problema durante la llamada: {}", e.getMessage());
			
			changeStatusOfParticipantsInClosureWithError();
		} finally {
			log.info("Finaliza la llamada con el cliente {}", clientName);
			this.clientRequest.finalizeRequest();
			
			//Marcamos el objeto observable como objeto que ha cambiado.
	        setChanged();
	        //Notificamos a los observadores que el hilo ha terminado.
	        notifyObservers(CallState.FINISHED);
		}		        
	}
	
	private void changeStatusOfParticipantsInitially() throws StateException {
		if (EmployeeState.BUSY.equals(this.employee.getState())) {
			throw new StateException("Incorrect state. The employee was already busy.");
		}
		
		this.employee.setState(EmployeeState.BUSY);
		
		if (ClientRequestState.IN_ATENTION.equals(this.clientRequest.getState())) {
			throw new StateException("Incorrect status. The client has already been attended.");
		}
		
		this.clientRequest.setState(ClientRequestState.IN_ATENTION);		
	}
	
	private void changeStatusOfParticipantsInClosureSuccessfully() {
		this.clientRequest.setState(ClientRequestState.ATTENDED);		
		this.employee.setState(EmployeeState.AVAILABLE);				
	}
	
	private void changeStatusOfParticipantsInClosureWithError() {
		this.clientRequest.setState(ClientRequestState.CANCELLED);
		this.employee.setState(EmployeeState.AVAILABLE);				
	}
	
	protected abstract void answerCall() throws CallException;
	
	protected long getAttentionTimeInMilliseconds() {		
		return ((int) Math.round(this.random.nextDouble() * DIFFERENCE_TIME_MAXIMUM_AND_MINIMUM_CALL_SECONDS 
				+ MINIMUM_TIME_CALL_SECONDS)) * 1000;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((threadId == null) ? 0 : threadId.hashCode());
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
		EmployeeBusiness other = (EmployeeBusiness) obj;
		if (threadId == null) {
			if (other.threadId != null)
				return false;
		} else if (!threadId.equals(other.threadId))
			return false;
		return true;
	}	
}
