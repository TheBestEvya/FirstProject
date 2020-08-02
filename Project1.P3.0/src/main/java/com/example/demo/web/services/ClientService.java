package com.example.demo.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repos.CompanyRepo;
import com.example.demo.repos.CouponRepo;
import com.example.demo.repos.CustomerRepo;

@Service
public abstract  class ClientService {
	@Autowired
		protected CompanyRepo compRepo;
	@Autowired
		protected CouponRepo coupRepo;
	@Autowired
		protected CustomerRepo custRepo;

		public abstract boolean login(String email ,String password);

		public ClientService(CompanyRepo compRepo, CouponRepo coupRepo, CustomerRepo custRepo) {
			super();
			this.compRepo = compRepo;
			this.coupRepo = coupRepo;
			this.custRepo = custRepo;
		}

		

		
		
		

}
