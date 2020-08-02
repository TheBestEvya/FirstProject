package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.beans.Company;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
	
	boolean existsByNameAndEmail(String name ,String email);
	boolean existsByEmailAndPassword(String email ,String password);
	Company findByEmail(String email);
}
