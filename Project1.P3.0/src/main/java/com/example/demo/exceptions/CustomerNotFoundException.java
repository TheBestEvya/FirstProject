package com.example.demo.exceptions;

public class CustomerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	public CustomerNotFoundException() {
		super("Customer not found !");
	}

}
