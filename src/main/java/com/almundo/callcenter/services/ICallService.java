package com.almundo.callcenter.services;

import com.almundo.callcenter.entities.ClientRequest;
import com.almundo.callcenter.exceptions.ValidationException;

public interface ICallService {

	void answerCall(ClientRequest request) throws ValidationException;

}
