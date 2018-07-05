package com.almundo.callcenter.business;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.almundo.callcenter.business.strategies.EmployeeBusiness;
import com.almundo.callcenter.business.strategies.EmployeeFactory;
import com.almundo.callcenter.entities.CallState;
import com.almundo.callcenter.entities.ClientRequest;
import com.almundo.callcenter.entities.ClientRequestState;
import com.almundo.callcenter.entities.Employee;
import com.almundo.callcenter.entities.EmployeeState;
import com.almundo.callcenter.repositories.IEmployeeRepository;

@Component
public class Dispatcher implements Observer, IDispatcher {
	
	private static final int MAXIMUM_AMOUNT_OF_THREADS = 10;
	
	@Autowired
	private IEmployeeRepository employeeRepository;
	
	private Set<Employee> busyEmployees;
	private SortedSet<Employee> availableEmployees;	
	private Queue<ClientRequest> clientRequests;
	private Set<EmployeeBusiness> callThreads;
	
	public Dispatcher() {
				
	}
	
	@PostConstruct
	private void init() {
		this.availableEmployees = this.employeeRepository.getAvailableEmployees();
		
		if (this.availableEmployees != null && !this.availableEmployees.isEmpty()) {
			this.availableEmployees.forEach(employee -> employee.addObserver(this));
		}		
	}
	
	@Override
	public void dispatchCall(ClientRequest clientRequest) {		
		clientRequest.addObserver(this);
		
		// Verifico si hay empleados disponibles. En caso de que no lo hayan, encolo la llamada, en espera de que pueda ser atendida.
		if (this.availableEmployees == null || this.availableEmployees.isEmpty()) {
			loadClientOnHold(clientRequest);
			
			return ;
		}
		
		/*
		 * Verifico si se llego a la maxima cantidad de llamadas atendidas en paralelo. Si se llego al maximo, 
		 * encolo la llamada, en espera de que pueda ser atendida.
		 */
		if (this.callThreads != null && this.callThreads.size() == MAXIMUM_AMOUNT_OF_THREADS) {
			loadClientOnHold(clientRequest);
			
			return ;
		}
		
		int numberOfThread = (this.callThreads == null ? 1 : (this.callThreads.size() + 1));
		String threadId = "Thread - " + numberOfThread;
		
		Employee employee = this.availableEmployees.first();
		this.availableEmployees.remove(employee);
		
		EmployeeBusiness employeeBusiness = EmployeeFactory.createEmployeeBusiness(threadId, employee, clientRequest);
				
		Thread employeeBusinessThread = new Thread(employeeBusiness, threadId);
		
		if (this.callThreads == null) {
			this.callThreads = new HashSet<EmployeeBusiness>();
		}
		
		employeeBusiness.addObserver(this);
		this.callThreads.add(employeeBusiness);				
		employeeBusinessThread.start();		
	}

	private void loadClientOnHold(ClientRequest clientRequest) {
		if (this.clientRequests == null) {
			this.clientRequests = new ArrayDeque<ClientRequest>();			
		}
		
		clientRequest.setState(ClientRequestState.ON_HOLD);
		this.clientRequests.add(clientRequest);
	}

	@Override
	public void update(Observable observable, Object state) {		
		if (state != null && state instanceof EmployeeState) {
			EmployeeState employeeState = (EmployeeState) state;
			Employee employee = (Employee) observable;
			
			updateEmployees(employee, employeeState);
		} else if (state != null && state instanceof ClientRequestState) {
			ClientRequestState clientRequestState = (ClientRequestState) state;
			ClientRequest clientRequest = (ClientRequest) observable;
			
			updateClientRequest(clientRequest, clientRequestState);
		} else if (state != null && state instanceof CallState) {
			CallState callState = (CallState) state;
			EmployeeBusiness employeeBusiness = (EmployeeBusiness) observable;
			
			updateEmployeeBusiness(employeeBusiness, callState);
		}
	}
	
	private void updateEmployeeBusiness(EmployeeBusiness employeeBusiness, CallState callState) {
		if (CallState.FINISHED.equals(callState)) {			
			this.callThreads.remove(employeeBusiness);
			
			if (this.clientRequests != null && !this.clientRequests.isEmpty()) {
				dispatchCall(this.clientRequests.poll());
			}
		}
	}

	private void updateEmployees(Employee employee, EmployeeState state) {		
		if (EmployeeState.AVAILABLE.equals(state)) {
			if (this.availableEmployees == null) {
				this.availableEmployees = new TreeSet<Employee>();
			}
			
			this.availableEmployees.add(employee);
			this.busyEmployees.remove(employee);
		} else {
			if (this.busyEmployees == null) {
				this.busyEmployees = new HashSet<Employee>();
			}
			
			this.availableEmployees.remove(employee);
			this.busyEmployees.add(employee);
		} 
	}
	
	private void updateClientRequest(ClientRequest clientRequest, ClientRequestState clientRequestState) {
		if (this.clientRequests != null && !ClientRequestState.ON_HOLD.equals(clientRequestState)) {
			this.clientRequests.remove(clientRequest);
		}
	}
}
