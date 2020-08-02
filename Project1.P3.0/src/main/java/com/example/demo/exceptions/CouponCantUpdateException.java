package com.example.demo.exceptions;

public class CouponCantUpdateException extends Exception {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;

	public CouponCantUpdateException() {
	super("Coupon cannot be updated , check Coupon ID and Company ID!");
	}

}
