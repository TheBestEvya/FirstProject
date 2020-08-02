package com.example.demo.repos;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

	Set<Coupon> findByTitle(String title);

	Set<Coupon> findByCompanyId(int id);

	Set<Coupon> findByEndDateAfter(Date now);

	Set<Coupon> findByCompanyIdAndCategory(int id, CategoryType category);

	Set<Coupon> findByCompanyIdAndPriceLessThan(int id, double maxPrice);
	
	boolean existsByCompanyIdAndTitle(int compId , String title);
	
	
}
