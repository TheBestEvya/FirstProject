package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.beans.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	boolean existsByEmail(String email);
	boolean existsByEmailAndPassword(String email , String password);
	Customer findByEmail(String email);
}
