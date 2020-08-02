package com.example.demo.web.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Coupon;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponCantUpdateException;
import com.example.demo.exceptions.CouponExistsException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.web.Session;
import com.example.demo.web.services.CompanyService;

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

		@Autowired
	private Map<String, Session> sessions;
	
	@PostMapping("/addCoup/{token}")
	public ResponseEntity<?> addCoupon(@PathVariable String token ,@RequestBody Coupon coupon){
		Session session = sessions.get(token);
		CompanyService cf = (CompanyService) session.getCf();
		try {
			Coupon coupon1 = cf.addCoupon(coupon);
			return ResponseEntity.ok(coupon1);
		} catch (CouponExistsException | CompanyNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/upCoup/{token}")
	public ResponseEntity<?> updateCoupon(@PathVariable String token ,@RequestBody Coupon coupon){
		Session session = sessions.get(token);
		CompanyService cf = (CompanyService) session.getCf();
		try {
			cf.updateCoupon(coupon);
			return ResponseEntity.ok("Coupon ID" +coupon.getId()+"Updated!");
		} catch (CouponCantUpdateException | CouponNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/oneCoup/{token}/{coupId}")
	public ResponseEntity<?> getOneCoup(@PathVariable String token ,@PathVariable int coupId){
		Session session = sessions.get(token);
		CompanyService cf = (CompanyService) session.getCf();
		try {
			return ResponseEntity.ok(cf.getOneCoup(coupId));
		} catch (CouponNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@DeleteMapping("/delCoup/{token}/{coupId}")
	public ResponseEntity<?> deleteCoupon(@PathVariable String token ,@PathVariable int coupId){
		Session session = sessions.get(token);
		CompanyService cf = (CompanyService) session.getCf();
		try {
			cf.deleteCoupon(coupId);
			return ResponseEntity.ok("Coupon ID" + coupId + "deleted");
		} catch (CouponNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/allCoups/{token}")
	public ResponseEntity<?> getCompCoupons(@PathVariable String token){
		Session session = sessions.get(token);
		CompanyService cf = (CompanyService) session.getCf();
		return ResponseEntity.ok(cf.getCompCoupons());
	}
	@GetMapping("/allCoupsByC/{token}/{category}")
	public ResponseEntity<?> getCompCouponsCat(@PathVariable String token ,@PathVariable String category){
		Session session = sessions.get(token);
		CompanyService cf = (CompanyService) session.getCf();
		return ResponseEntity.ok(cf.getCompCouponsCat(CategoryType.values()[Integer.parseInt(category)]));
	}
	
	@GetMapping("/allCoupsByMaxP/{token}/{maxPrice}")
	public ResponseEntity<?> getCompCouponsMaxPrice(@PathVariable String token, @PathVariable double maxPrice){
		Session session = sessions.get(token);
		CompanyService cf = (CompanyService) session.getCf();
		return ResponseEntity.ok(cf.getCompCouponsMaxPrice(maxPrice));
	}
	
	@GetMapping("/compDets/{token}")
	public ResponseEntity<?> getCompDets(@PathVariable String token){
		Session session = sessions.get(token);
		CompanyService cf = (CompanyService) session.getCf();
		return ResponseEntity.ok(cf.getCompDetails());
	}
}
