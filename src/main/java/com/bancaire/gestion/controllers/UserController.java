package com.bancaire.gestion.controllers;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bancaire.gestion.entities.User;
import com.bancaire.gestion.entities.Role;
import com.bancaire.gestion.repositories.UserRepository;
import com.bancaire.gestion.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;



//USER

@Controller
@RequestMapping("user")
public class UserController {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	//@Autowired
	public UserController(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository= roleRepository; 
	}

	@GetMapping("detail")
    public String afficherDetail() {
        return "user/OwnerDetails";
    }
	
	@RestController
	@RequestMapping("/login")
	public class LoginController {
	    
	    @Autowired
	    private UserRepository userRepository; // ✅ Inject the user repository

	    @Autowired
	    private PasswordEncoder passwordEncoder; // ✅ Add password encoder for secure comparison

	    @PostMapping
	    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
	        // ✅ Hardcoded backdoor login
	        if (email.equals("admin") && password.equals("admin")) {
	            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/admin/UserList")).build();
	        }

	        // ✅ Fetch user from the database
	        User user = userRepository.findByEmail(email).orElse(null);
	        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
	        }

	        // ✅ Redirect based on role
	        String redirectUrl = (user.getRole().getId() == 1) ? "/admin/UserList" : "/user/OwnerDetails";
	        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
	    }

	    // ✅ Ensure the login page is mapped correctly
	    @GetMapping
	    public ModelAndView loginPage() {
	        return new ModelAndView("Login"); // ✅ Return Thymeleaf login template
	    }
	}

	
	@PostMapping("deposit/{id}")
	public String depot (@ModelAttribute("user") User user, @RequestParam("userId") long id, @RequestParam("balance") double balance) {
		User u=userRepository.getById(id);
		u.setBalance(user.getBalance()+balance);
		userRepository.save(u);
		return "/user/OwnerDetails";
	}
	
	@PostMapping("retrait/{id}")
	public String retrait (@ModelAttribute("user") User user, @RequestParam("userId") long id, @RequestParam("balance") double balance) {
		User u=userRepository.getById(id);
		if(u.getBalance()>0) {
		u.setBalance(user.getBalance()-balance); }
		userRepository.save(u);
		return "/user/OwnerDetails";
	}



	

	


	
}
