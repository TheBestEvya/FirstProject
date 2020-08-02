package com.example.demo.exceptions;

public class CompanyExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;

	public CompanyExistsException() {
		super("Company is already exists!");
	}

}
