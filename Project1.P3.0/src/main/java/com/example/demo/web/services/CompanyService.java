package com.example.demo.web.services;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponCantUpdateException;
import com.example.demo.exceptions.CouponExistsException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.repos.CompanyRepo;
import com.example.demo.repos.CouponRepo;
import com.example.demo.repos.CustomerRepo;

@Service
@Scope("prototype")
public class CompanyService extends ClientService {
	private int companyId;

	public CompanyService(CompanyRepo compRepo, CouponRepo coupRepo, CustomerRepo custRepo) {
		super(compRepo, coupRepo, custRepo);
	}

	@Override
	public boolean login(String email, String password) {
		if (compRepo.existsByEmailAndPassword(email, password)) {
			companyId = compRepo.findByEmail(email).getId();
			return true;
		} else
			return false;
	}

	public Coupon addCoupon(Coupon coupon) throws CouponExistsException, CompanyNotFoundException {
//		coupon.setCompany(getCompDetails());
		if (coupRepo.existsByCompanyIdAndTitle(companyId, coupon.getTitle()))
			throw new CouponExistsException();
		
		return coupRepo.save(coupon);
	}

	public void updateCoupon(Coupon coupon) throws CouponCantUpdateException, CouponNotFoundException {
//			coupon.setCompany(getCompDetails());
		Coupon c1 = getOneCoup(coupon.getId());// checks if the coupon id and the company id are the same as mentioned
		if (!(c1.getId() == coupon.getId()))
			throw new CouponCantUpdateException();

		coupRepo.save(coupon);

	}

	public Coupon getOneCoup(int coupId) throws CouponNotFoundException {
		return coupRepo.findById(coupId).orElseThrow(CouponNotFoundException::new);
	}

	public void deleteCoupon(int coupId) throws CouponNotFoundException {
		Coupon coup = coupRepo.findById(coupId).orElseThrow(CouponNotFoundException::new);
		for (Customer c : custRepo.findAll()) {//goes on all of the customers , and removes the coupon from their "possesion"
			c.getCoupons().remove(coup);
			custRepo.save(c);
		}
//		coupRepo.deleteHByCoupId(coupId); // deletes coupon purchase history
		coupRepo.deleteById(coupId);
	}

	public Set<Coupon> getCompCoupons() {
		return coupRepo.findByCompanyId(companyId);
	}

	public Set<Coupon> getCompCouponsCat(CategoryType category) {
		return coupRepo.findByCompanyIdAndCategory(companyId , category);
	}

	public Set<Coupon> getCompCouponsMaxPrice(double maxPrice) {
		return coupRepo.findByCompanyIdAndPriceLessThan(companyId, maxPrice);

	}

	public Company getCompDetails() {
		return compRepo.findById(companyId).orElse(null);
	}
}
