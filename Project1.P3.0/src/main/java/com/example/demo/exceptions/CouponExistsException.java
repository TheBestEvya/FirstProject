package com.example.demo.exceptions;

public class CouponExistsException extends Exception {
		
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 8L;

		public CouponExistsException() {
			super("Coupon is alreay exists! , please check the title");
		}
}
