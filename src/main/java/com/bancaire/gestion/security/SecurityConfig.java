	package com.bancaire.gestion.security;
	
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.web.SecurityFilterChain;
	
	@Configuration
	public class SecurityConfig {
	
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.authorizeHttpRequests(auth -> auth
	            .requestMatchers("/accueil","/css/**", "/images/**").permitAll()  // Allow public access + static resources
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
	}
