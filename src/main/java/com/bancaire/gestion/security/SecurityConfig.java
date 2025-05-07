	package com.bancaire.gestion.security;
	
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.web.SecurityFilterChain;
	
	import org.springframework.security.core.userdetails.User;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.security.provisioning.InMemoryUserDetailsManager;
	
	@Configuration
	public class SecurityConfig {
		
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.authorizeHttpRequests(auth -> auth
	        	.requestMatchers("/admin/accueil").permitAll()
	        	.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // ✅ Restrict admin pages    
	        	.requestMatchers("/user/**").hasAuthority("ROLE_USER") // ✅ Restrict user 
	            .anyRequest().authenticated() // ✅ Require login for all other pages
	        	//.requestMatchers("/accueil","/css/**", "/images/**").permitAll()  // Allow public access + static resources
	        	//.requestMatchers("/user/accueil","/user/list","/details/*","/depot/*","/retrait/*").permitAll()
	        	//.requestMatchers("/comptes", "/ajouter").authenticated() // Require login for protected pages
	        )
	        .formLogin(login -> login
	        	    .successHandler((request, response, authentication) -> { // ✅ Custom redirect based on role
	        	        String role = authentication.getAuthorities().iterator().next().getAuthority();

	        	        if (role.equals("ROLE_ADMIN")) {
	        	            response.sendRedirect("/admin/UserList"); // ✅ Redirect Admins correctly
	        	        } else {
	        	            response.sendRedirect("/user/OwnerDetails"); // ✅ Redirect Users correctly
	        	        }
	        	    })
	        )
	        .logout(logout -> logout
	            //.logoutSuccessUrl("/accueil") // Redirect to home after logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // ✅ Redirect to login after logout
	            .permitAll()
	        );
	
	        return http.build();
	    }	
	   

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    } 
	}
