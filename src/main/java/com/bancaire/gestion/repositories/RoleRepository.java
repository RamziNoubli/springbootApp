package com.bancaire.gestion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancaire.gestion.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
		
}


