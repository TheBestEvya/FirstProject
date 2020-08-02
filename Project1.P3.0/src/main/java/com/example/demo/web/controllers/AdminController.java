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

import com.example.demo.beans.Company;
import com.example.demo.beans.Customer;
import com.example.demo.exceptions.CompanyCannotBeUpdatedException;
import com.example.demo.exceptions.CompanyExistsException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CustomerExistsException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.web.Session;
import com.example.demo.web.services.AdminService;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	
	@Autowired
	private Map<String, Session> sessions;

		
	
	
	@PostMapping("/addComp/{token}")
	public ResponseEntity<?> addCompany(@PathVariable String token, @RequestBody Company company) {
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		try {
			af.addCompany(company);
			return ResponseEntity.ok(company);
		} catch (CompanyExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	@PutMapping("/upComp/{token}")
	public ResponseEntity<?> updateCompany(@PathVariable String token, @RequestBody Company company) {
		System.out.println(company);
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		try {
			af.updateCompany(company);
			return ResponseEntity.ok(company);
		} catch (CompanyCannotBeUpdatedException | CompanyNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("/delComp/{token}/{compId}")
	public ResponseEntity<?> deleteCompany(@PathVariable String token, @PathVariable int compId) {
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		try {
			af.deleteComp(compId);
			return ResponseEntity.ok("Company ID" + compId + "Deleted");
		} catch (CompanyNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/allComps/{token}")
	public ResponseEntity<?> getAllComp(@PathVariable String token) {
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		return ResponseEntity.ok(af.getAllComp());
	}
	
	@GetMapping("/oneComp/{token}/{compId}")
	public ResponseEntity<?> getOneComp(@PathVariable String token, @PathVariable int compId) {
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		try {
			return ResponseEntity.ok(af.getOneCompany(compId));
		} catch (CompanyNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/addCust/{token}")
	public ResponseEntity<?> addNewCust(@PathVariable String token, @RequestBody Customer customer) {
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		try {
			af.addNewCust(customer);
			return ResponseEntity.ok(customer);
		} catch (CustomerExistsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/delCust/{token}/{custId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String token, @PathVariable int custId) {
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		try {
			af.deleteCust(custId);
			return ResponseEntity.ok("Customer ID" + custId + "Deleted");
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/upCust/{token}")
	public ResponseEntity<?> updateCustomer(@PathVariable String token, @RequestBody Customer customer) {
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		try {
			af.updateCust(customer);
			return ResponseEntity.ok(customer);
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/allCusts/{token}")
	public ResponseEntity<?> getAllCust(@PathVariable String token) {
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		return ResponseEntity.ok(af.getAllCust());
	}

	@GetMapping("oneCust/{token}/{custId}")
	public ResponseEntity<?> getOneCust(@PathVariable String token, @PathVariable int custId) {
		Session session = sessions.get(token);
		AdminService af = (AdminService) session.getCf();
		try {
			Customer c1 =af.getOneCust(custId);
			return ResponseEntity.status(HttpStatus.OK).body(c1);
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
