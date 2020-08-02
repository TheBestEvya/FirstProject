package com.example.demo.jobs;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.repos.CouponRepo;
import com.example.demo.repos.CustomerRepo;

@Scope
public class CouponExpirationDailyJob implements Runnable {

	@Autowired
	private CouponRepo cRepo;
	@Autowired
	private CustomerRepo custRepo;

	private boolean quit = false;

	public void stopJob() {
		quit = true;
	}

	@Override
	public void run() {
		while (quit == false) {
			Date now = new Date(System.currentTimeMillis());
			for (Coupon c : cRepo.findByEndDateAfter(now)) {
				for (Customer c1 : custRepo.findAll()) {
					c1.getCoupons().remove(c);
					custRepo.save(c1);
				}
				cRepo.delete(c);
			}
			try {
				Thread.sleep(1000 * 60 * 60 * 24);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
