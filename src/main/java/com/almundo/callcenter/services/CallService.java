package com.almundo.callcenter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.business.Dispatcher;
import com.almundo.callcenter.entities.ClientRequest;
import com.almundo.callcenter.exceptions.ValidationException;

@Service
public class CallService implements ICallService {

	@Autowired
	private Dispatcher dispatcher;

	@Override
	public void answerCall(ClientRequest request) throws ValidationException {		
		if (request == null || request.getClient() == null) {
			throw new ValidationException("No hay datos asociados al cliente que ha llamado.");
		}
		
		if (request.getClient().getClientCode() == null || request.getClient().getClientCode().trim().isEmpty()) {
			throw new ValidationException("No se ha podido identificar al cliente.");
		}
		
		if (request.getClient().getDni() == null || request.getClient().getDni() <= 0) {
			throw new ValidationException("Dni del cliente incorrecto.");
		}
		
		if (request.getClient().getName() == null || request.getClient().getName().trim().isEmpty()) {
			throw new ValidationException("El cliente debe de informar su nombre.");
		}
		
		dispatcher.dispatchCall(request);
	}
	
	
}
