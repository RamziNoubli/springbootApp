package com.bancaire.gestion.entities;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Role {
	@Id
	@GeneratedValue
	private long id;
	private String name; //USER or ADMIN
	
	@OneToMany(mappedBy = "role") // ✅ One role can be assigned to MANY users
	private List<User> users;

	public Role(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public Role() {}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
