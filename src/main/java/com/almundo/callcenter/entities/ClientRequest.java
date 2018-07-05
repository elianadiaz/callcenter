package com.almundo.callcenter.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Observable;

public class ClientRequest extends Observable implements Serializable {

	private static final long serialVersionUID = -6852997360301175135L;

	private Client client;
	private LocalTime requestEntry;
	private LocalTime requestFinalization;
	private ClientRequestState state;
		
	public ClientRequest(Client client) {
		super();
		this.client = client;
		this.requestEntry = now();
	}

	public Client getClient() {
		return client;
	}

	public LocalTime getRequestEntry() {
		return requestEntry;
	}

	public void setRequestEntry(LocalTime requestEntry) {
		this.requestEntry = requestEntry;
	}

	public LocalTime getRequestFinalization() {
		return requestFinalization;
	}

	public void finalizeRequest() {
		this.requestFinalization = now();
	}
	
	private static LocalTime now() {
		return LocalTime.now(ZoneId.of(ZoneOffset.UTC.getId()));
	}
	
	public ClientRequestState getState() {
		return state;
	}

	public void setState(ClientRequestState state) {
		this.state = state;
		
		//Marcamos el objeto observable como objeto que ha cambiado
        setChanged();
        //Notificamos a los observadores y le enviamos el nuevo valor
        notifyObservers(this.state);
        //notifyObservers(); Este metodo solo notifica que hubo cambios en el objeto
	}
}
