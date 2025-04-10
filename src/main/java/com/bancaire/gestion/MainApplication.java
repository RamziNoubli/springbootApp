package com.bancaire.gestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bancaire.gestion.entities.Compte;
import java.util.List;
import java.util.ArrayList;

@SpringBootApplication
public class MainApplication {
	public static List<Compte> comptes = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		Compte c1 = new Compte(1,"Ramzi",2300.0);
		Compte c2 = new Compte(2,"Ahmed",4500.0);
		Compte c3 = new Compte(3,"Mark",3000.0);
		comptes.add(c1);
		comptes.add(c2);
		comptes.add(c3);
	}

}
