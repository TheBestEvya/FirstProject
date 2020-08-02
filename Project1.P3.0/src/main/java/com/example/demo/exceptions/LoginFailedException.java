package com.example.demo.exceptions;

public class LoginFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9L;
	public LoginFailedException() {
	super("Login has failed , Make sure Email , Password and Client type correct");
	}

}
