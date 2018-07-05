package com.almundo.callcenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.almundo.callcenter.entities.Client;
import com.almundo.callcenter.entities.ClientRequest;
import com.almundo.callcenter.entities.ClientRequestState;
import com.almundo.callcenter.exceptions.ValidationException;
import com.almundo.callcenter.services.ICallService;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallcenterApplicationTests {
	
	@Autowired
	private ICallService callService;
	
	@Test
	public void answerCallTest() {
		List<ClientRequest> clientRequests = new ArrayList<ClientRequest>();
		
		clientRequests.add(new ClientRequest(new Client("client-1", "Primer cliente", 11222333)));
		clientRequests.add(new ClientRequest(new Client("client-2", "Segundo cliente", 21333444)));
		clientRequests.add(new ClientRequest(new Client("client-3", "Tercero cliente", 31222333)));
		clientRequests.add(new ClientRequest(new Client("client-4", "Cuarto cliente", 41222333)));
		clientRequests.add(new ClientRequest(new Client("client-5", "Quinto cliente", 51222333)));
		clientRequests.add(new ClientRequest(new Client("client-6", "Sexto cliente", 61222333)));
		clientRequests.add(new ClientRequest(new Client("client-7", "Septimo cliente", 71222333)));
		clientRequests.add(new ClientRequest(new Client("client-8", "Octavo cliente", 81222333)));
		clientRequests.add(new ClientRequest(new Client("client-9", "Noveno cliente", 91222333)));
		clientRequests.add(new ClientRequest(new Client("client-10", "Decimo cliente", 10222333)));
		clientRequests.add(new ClientRequest(new Client("client-11", "Decimo primero cliente", 11000111)));
		clientRequests.add(new ClientRequest(new Client("client-12", "Decimo segundo cliente", 12111222)));
		clientRequests.add(new ClientRequest(new Client("client-13", "Decimo tercero cliente", 132223333)));
		clientRequests.add(new ClientRequest(new Client("client-14", "Decimo cuarto cliente", 14333444)));
		clientRequests.add(new ClientRequest(new Client("client-15", "Decimo quinto cliente", 12555666)));
		
		clientRequests.forEach(request -> {
			try {
				callService.answerCall(request);
			} catch (ValidationException e) {
				e.printStackTrace();
			}
		});
		
		Predicate<ClientRequest> findClientRequest = c -> !ClientRequestState.ON_HOLD.equals(c.getState());
		
		long amountOfRequirementsProcessed = clientRequests.stream().filter(findClientRequest).count();
		
		// Dejo lo siguiente comentado por si quieren ver en que estado se encuentran las llamadas
		/*System.out.println("CANTIDAD Q FUERON ATENDIDOS: " + amountOfRequirementsProcessed);
		clientRequests.forEach(request -> {
			System.out.println("ESTADO DEL REQUEST: FINALIZADO: " + request.getRequestFinalization() + " - ESTADO: " + request.getState());
		});
		*/
		
		boolean isOkey = (amountOfRequirementsProcessed >= 10);
		
		assertTrue(isOkey);	
	}
	
	@Test
	public void answerCallIndividualWithErrorTest() {
		
		ClientRequest clientRequest = new ClientRequest(new Client("client-1", "   ", 11222333));
		
		boolean isIncorrect = false;
		
		try {
			callService.answerCall(clientRequest);
		} catch (ValidationException e) {
			isIncorrect = true;
		}
			
		assertTrue(isIncorrect);	
	}
	
	@Test
	public void answerCallIndividualSuccessfulTest() {
		
		ClientRequest clientRequest = new ClientRequest(new Client("client-1", "Primer cliente", 11222333));
		
		boolean isIncorrect = false;
		
		try {
			callService.answerCall(clientRequest);
		} catch (ValidationException e) {
			isIncorrect = true;
		}
			
		assertTrue(!ClientRequestState.ON_HOLD.equals(clientRequest.getState()) && !isIncorrect);	
	}
}
