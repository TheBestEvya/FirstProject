package com.example.demo.beans;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "customrs")
public class Customer {
	public Customer() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String email;
	@Column
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(inverseJoinColumns = @JoinColumn(name="coup_id"),
    joinColumns = @JoinColumn(name="cust_id"),
    name="customers_vs_coupons")

	private Set<Coupon> coupons;

	public Customer(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public Set<Coupon> getCoupons() {
		return coupons;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}

	@Override
	public boolean equals(Object cust) {
		if (cust instanceof Customer) {
			if ((((Customer) cust).getId()) == this.getId()
					&& (((Customer) cust).getFirstName()).equals(this.getFirstName())) {
				return true;
			}

		}
		return false;
	}

	
}