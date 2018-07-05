package com.almundo.callcenter.business.strategies;

import com.almundo.callcenter.exceptions.CallException;

public class NullEmployeeBusiness extends EmployeeBusiness {

	public NullEmployeeBusiness(String threadId) {
		super(threadId, null, null);
	}

	@Override
	protected void answerCall() throws CallException {
		log.warn("Hubo un problema para especificar como se va a atender la llamada.");
		
		// Lanzo la excepcion porque la manipulo en el metodo principal.
		throw new CallException("[FIN - NULL OBJECT] - Hubo un problema para especificar como se va a atender la llamada.");
	}

}
