package com.bancaire.gestion.controllers;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
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

import java.io.File;
import java.io.IOException;
import java.util.UUID; 



//ADMIN
	
@Controller
@RequestMapping("admin")
public class AdminController {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	//@Autowired
	public AdminController(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository= roleRepository; 
	}
	
	@GetMapping("accueil")
    public String afficherAccueil() {
        return "HomePage";
    }
	
	@GetMapping("list")
	public String afficher(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		
		return "/admin/UserList";
		
	}
	
	//ADING A USER
	@GetMapping	("add")
	public String ajouter(Model model) {
		model.addAttribute("user", new User());
		return "/admin/UserAdd";
	}
	
	@PostMapping ("add")
	public String add(@ModelAttribute User user, @RequestParam("role_Id") long role_Id,
			@RequestParam("imageFile") MultipartFile imageFile) throws IllegalStateException, IOException {
		Role role = roleRepository.getById(role_Id); // ✅ Fetch role from DB
		
		if (!imageFile.isEmpty()) {
	        String imagePath = saveImage(imageFile); // ✅ Save image file
	        user.setProfile(imagePath); // ✅ Store URL in the database
	    }
		
	    user.setRole(role); 
		userRepository.save(user);
		return "redirect:/admin/list";
	}
	
	private String saveImage(MultipartFile imageFile) throws IllegalStateException, IOException {
		String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
	    File file = new File("uploads/" + fileName);

	    imageFile.transferTo(file); // ✅ Save image file
	    return "/uploads/" + fileName; // TODO Auto-generated method stub
	}

	//USER DETAILS
	@GetMapping("details/{id}")
	public String maj (@PathVariable("id") long id, Model model) {
		User u = userRepository.getById(id);
		model.addAttribute("users",u);
		return "admin/UserDetails";
	}
	
	//DELETE A USER
	@GetMapping("delete/{id}")
	public String supp (@PathVariable("id") long id) {
		User u = userRepository.getById(id);
		userRepository.delete(u);
		return "redirect:/admin/list";
	}
	
	//EDIT A USER
	@PostMapping("update")
	public String modifier (@ModelAttribute("user") User user, @RequestParam("userId") long id) {
		User u=userRepository.getById(id);
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		userRepository.save(u);
		return "redirect:/admin/list";
	}
	
	
	


}
