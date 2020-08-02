package com.example.demo.exceptions;

public class LoginRequiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7L;
	public LoginRequiredException() {
	super("Login required , make sure you are logged in!");
	}
}
