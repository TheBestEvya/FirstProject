package com.example.demo.exceptions;

public class CouponNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;

	public CouponNotFoundException() {
		super("Coupon not found!");
	}
}
