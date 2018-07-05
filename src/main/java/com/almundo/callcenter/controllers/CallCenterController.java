package com.almundo.callcenter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almundo.callcenter.entities.Client;
import com.almundo.callcenter.entities.ClientRequest;
import com.almundo.callcenter.exceptions.ValidationException;
import com.almundo.callcenter.exceptions.entities.ErrorMessage;
import com.almundo.callcenter.services.ICallService;

@RestController
@RequestMapping("/callcenter")
public class CallCenterController {

	@Autowired
	private ICallService callService;
	
	@PostMapping("/")
	public ResponseEntity<?> answerCall(@RequestBody Client client, @RequestHeader(value="Client") String clientCode) {
		if (client != null) {
			client.setClientCode(clientCode);
		}
		
		ClientRequest request = new ClientRequest(client);
		try {
			callService.answerCall(request);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (ValidationException ex) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setHttpStatus(HttpStatus.BAD_REQUEST.value());
			errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
	        errorMessage.setMessage(ex.getMessage());
	        
			return ResponseEntity.badRequest().body(errorMessage);
		}				
	}
}
