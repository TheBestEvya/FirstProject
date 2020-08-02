package com.example.demo.web.services;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.exceptions.CompanyCannotBeUpdatedException;
import com.example.demo.exceptions.CompanyExistsException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CustomerExistsException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.repos.CompanyRepo;
import com.example.demo.repos.CouponRepo;
import com.example.demo.repos.CustomerRepo;

@Service
@Scope("prototype")
public class AdminService extends ClientService {

	public AdminService(CompanyRepo compRepo, CouponRepo coupRepo, CustomerRepo custRepo) {
		super(compRepo, coupRepo, custRepo);
	}

	@Override
	public boolean login(String email, String password) {
		if ("admin@admin.com".equals(email) && "admin".equals(password))
			return true;
		else
			return false;

	}

	public void addCompany(Company company) throws CompanyExistsException {
		if (compRepo.existsByNameAndEmail(company.getName(), company.getEmail()))// checks if there is a compnay with
																					// the same name and email
			throw new CompanyExistsException();

		compRepo.save(company);
	}

	public void updateCompany(Company company) throws CompanyCannotBeUpdatedException, CompanyNotFoundException {
		System.out.println(company);
		Company c1 = getOneCompany(company.getId());
		System.out.println(c1);
		if (!(c1.getId()== company.getId() && c1.getName().equals(company.getName()))) // if the company exist by the name and
			throw new CompanyCannotBeUpdatedException();
		
		
		compRepo.save(company);													// id save and update the comp


	}

	public void deleteComp(int compId) throws CompanyNotFoundException {
		Company c1 = compRepo.findById(compId).orElseThrow(CompanyNotFoundException::new);
		for (Coupon c : c1.getCoupons()) {
			for (Customer cust : getAllCust()) {
				cust.getCoupons().remove(c);
				custRepo.save(cust);
			}
			coupRepo.delete(c);
		}
		compRepo.deleteById(compId);// deletes the comp itself
		
//		coupRepo.deleteHByCompanyCompId(compId); // deletes coupon purchase history!
//		coupRepo.deleteCByCompanyCompId(compId); // delete all coupons of the company
		
	}

	public List<Company> getAllComp() {
		return compRepo.findAll();
	}

	public Company getOneCompany(int compId) throws CompanyNotFoundException {
		return compRepo.findById(compId).orElseThrow(CompanyNotFoundException::new);
	}

	public void addNewCust(Customer customer) throws CustomerExistsException {
		if (custRepo.existsByEmail(customer.getEmail())) // checks if the customer exist already by email, if not
			throw new CustomerExistsException();
															// creates a new customer

		custRepo.save(customer);
	}

	public void deleteCust(int custId) throws CustomerNotFoundException {
		Customer c1 = custRepo.findById(custId).orElseThrow(CustomerNotFoundException::new);
		c1.getCoupons().clear();// first clear the customers coupons
		custRepo.save(c1);// updates it
		custRepo.deleteById(custId);// finally deletes it
	}

	public void updateCust(Customer customer) throws CustomerNotFoundException {
		if (!custRepo.existsById(customer.getId())) 
		throw new CustomerNotFoundException();
		custRepo.save(customer);
	}

	public List<Customer> getAllCust() {
		return custRepo.findAll();
	}

	public Customer getOneCust(int custId) throws CustomerNotFoundException {
		return custRepo.findById(custId).orElseThrow(CustomerNotFoundException::new);
	}

}
