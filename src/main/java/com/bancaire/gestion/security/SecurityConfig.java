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
	            .requestMatchers("/accueil","/css/**", "/images/**").permitAll()  // Allow public access + static resources
	            .requestMatchers("/details/*","/depot/*","/retrait/*").permitAll()
	            .requestMatchers("/comptes", "/ajouter").authenticated() // Require login for protected pages
	        )
	        .formLogin(login -> login
	            .defaultSuccessUrl("/comptes", true) // Redirect after successful login
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/accueil") // Redirect to home after logout
	            .permitAll()
	        );
	
	        return http.build();
	    }
	    @Bean
	    public UserDetailsService userDetailsService() {
	        UserDetails user = User.builder()
	                .username("admin")  // Set custom username
	                .password(passwordEncoder().encode("password123"))  // Secure password with BCrypt	                .roles("USER")  // Assign role
	                .build();

	        return new InMemoryUserDetailsManager(user);
	    }
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	}
