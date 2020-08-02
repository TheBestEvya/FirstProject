package com.example.demo.web;

import com.example.demo.web.services.ClientService;

public class Session {

	private ClientService cf;
	private long lastAction;
	public Session(ClientService cf, long lastAction) {
		super();
		this.cf = cf;
		this.lastAction = lastAction;
	}
	public long getLastAction() {
		return lastAction;
	}
	public void setLastAction(long lastAction) {
		this.lastAction = lastAction;
	}
	
	public ClientService getCf() {
		return cf;
	}

	
}
