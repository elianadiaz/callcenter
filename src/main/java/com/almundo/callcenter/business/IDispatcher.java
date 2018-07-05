package com.almundo.callcenter.business;

import com.almundo.callcenter.entities.ClientRequest;

public interface IDispatcher {

	void dispatchCall(ClientRequest clientRequest);
}
