package com.example.demo.exceptions;

public class CustomerExistsException extends Exception {

		/**
	 * 
	 */
	private static final long serialVersionUID = 5L;

		public CustomerExistsException() {
			super("Customer already exists!");
		}
}
