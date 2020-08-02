package com.example.demo.web.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.exceptions.CantPurhcaseCouponException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.repos.CompanyRepo;
import com.example.demo.repos.CouponRepo;
import com.example.demo.repos.CustomerRepo;

@Service
@Scope("prototype")
public class CustomerService extends ClientService {
	private int customerId;

	public CustomerService(CompanyRepo compRepo, CouponRepo coupRepo, CustomerRepo custRepo) {
		super(compRepo, coupRepo, custRepo);
	}

	@Override
	public boolean login(String email, String password) {
		if (custRepo.existsByEmailAndPassword(email, password)) {
			customerId = custRepo.findByEmail(email).getId();
			return true;
		} else
			return false;
	}

	public void purchaseCoupon(int couponId)
			throws CouponNotFoundException, CustomerNotFoundException, CantPurhcaseCouponException {
		Coupon c1 = coupRepo.findById(couponId).orElseThrow(CouponNotFoundException::new);
		Customer cust1 = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		int amount = c1.getAmount();
		Date now = new Date();
		if (amount == 0 )
			throw new CantPurhcaseCouponException("No coupons left");//if there are coupons left
		
		else if( c1.getEndDate().before(now) )
				throw new CantPurhcaseCouponException("Coupon is expried"); // if the date is due 
		
			if(cust1.getCoupons().contains(c1)) 
				throw new CantPurhcaseCouponException("Coupon already bought!");	// checks if the coupon already bought
			
			c1.setAmount(amount - 1); // update the amount by -1
			coupRepo.save(c1);// saves the update to the coupon
			cust1.getCoupons().add(c1); // adds the coupon to the customer
			custRepo.save(cust1);// saves the update																																		


		
	}

	public Set<Coupon> getAllCouponsPurchased(){
		return getCustDetails().getCoupons();
	}
	

	public Set<Coupon> getAllCouponsByCategory(CategoryType category){
		Set<Coupon> couponOfSameCat = new HashSet<Coupon>();
		for (Coupon c : getAllCouponsPurchased()) {// checks if the coupon purchased by the customer are from the same category as requested
			if (c.getCategory().ordinal() == category.ordinal()) {
				couponOfSameCat.add(c);
			}
		}
		return couponOfSameCat;
	}

	public Set<Coupon> getAllCouponsUpMax(double maxPrice){
		Set<Coupon> couponsUpTo = new HashSet<Coupon>();
		for (Coupon c : getAllCouponsPurchased()) { // checks if the price of the coupons of customer is less than the maxPrice
			if (c.getPrice() <= maxPrice) {
				couponsUpTo.add(c);
			}
		}
		return couponsUpTo;
	}

	public Customer getCustDetails() {
		return custRepo.findById(customerId).orElse(null);
	}
	
	public List<Coupon> getAllCoupons() {
		return coupRepo.findAll();
	}

}
