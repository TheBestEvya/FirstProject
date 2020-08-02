package com.example.demo.web.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Coupon;
import com.example.demo.exceptions.CantPurhcaseCouponException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.web.Session;
import com.example.demo.web.services.CustomerService;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	@Autowired
	private Map<String, Session> sessions;

	@PostMapping("/purCoup/{token}/{coupId}")
	public ResponseEntity<?> purchaseCoupon(@PathVariable String token, @PathVariable int coupId) {
		Session session = sessions.get(token);
		CustomerService cf = (CustomerService) session.getCf();
		try {
			cf.purchaseCoupon(coupId);
			return ResponseEntity.ok("Coupon ID" + coupId + "purchased ");
		} catch (CouponNotFoundException | CustomerNotFoundException | CantPurhcaseCouponException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/coupsPur/{token}")
	public ResponseEntity<?> getAllCouponsPurchased(@PathVariable String token) {
		Session session = sessions.get(token);
		CustomerService cf = (CustomerService) session.getCf();
		return ResponseEntity.ok(cf.getAllCouponsPurchased());
	}

	@GetMapping("/coupsPurByCat/{token}/{category}")
	public ResponseEntity<?> getAllCouponsPurchasedByCategory(@PathVariable String token,
			@PathVariable String category) {
		Session session = sessions.get(token);
		CustomerService cf = (CustomerService) session.getCf();
			return ResponseEntity.ok(cf.getAllCouponsByCategory(CategoryType.values()[Integer.parseInt(category)]));
		
	}

	@GetMapping("/coupsPurByPrice/{token}/{maxPrice}")
	public ResponseEntity<?> getAllCouponsPurchasedUpMax(@PathVariable String token, @PathVariable double maxPrice) {
		Session session = sessions.get(token);
		CustomerService cf = (CustomerService) session.getCf();
		return ResponseEntity.ok(cf.getAllCouponsUpMax(maxPrice));
	}

	@GetMapping("/allCoupons/{token}")
	public ResponseEntity<?> getAllCoupons(@PathVariable String token) {
		Session session = sessions.get(token);
		CustomerService cf = (CustomerService) session.getCf();
		return ResponseEntity.ok(cf.getAllCoupons());
	}

	@GetMapping("/CustomerDets/{token}")
	public ResponseEntity<?> getCustDets(@PathVariable String token) {
		Session session = sessions.get(token);
		CustomerService cf = (CustomerService) session.getCf();
		return ResponseEntity.ok(cf.getCustDetails());
	}
	
	

}
