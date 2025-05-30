package com.bancaire.gestion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancaire.gestion.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {


}
