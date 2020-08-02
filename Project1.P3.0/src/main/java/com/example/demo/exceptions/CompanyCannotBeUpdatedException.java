package com.example.demo.exceptions;

public class CompanyCannotBeUpdatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	public CompanyCannotBeUpdatedException() {
	super("Company name or ID isn't exists");
	}

}
