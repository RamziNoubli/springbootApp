package com.bancaire.gestion.entities;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String email;
	private double balance=0.0;
	private String password;
    private String profile; // ✅ Store profile picture URL in the database
	
	@ManyToOne
	@JoinColumn(name = "role_id") // ✅ Each user has ONE role
	private Role role; 
	
	public User(long id, String name, String email, double balance, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.balance = balance;
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public User() {}
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance=balance;
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

	// ✅ Getter for profile picture URL
    public String getProfile() {
        return profile;
    }

    // ✅ Setter for profile picture URL
    public void setProfile(String profile) {
        this.profile = profile;
    }


}




