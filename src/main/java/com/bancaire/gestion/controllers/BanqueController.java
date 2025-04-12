package com.bancaire.gestion.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bancaire.gestion.entities.Compte;
import static com.bancaire.gestion.MainApplication.comptes;

@Controller
public class BanqueController {
	
	
	@GetMapping("accueil")
    public String afficherAccueil() {
        return "accueil";
    }

	@RequestMapping("comptes")
	public String afficher(Model model) {
		model.addAttribute("comptes",comptes);
		return "listeCompte";
	}
	
	@PostMapping("ajouter")
	public String add(@RequestParam("titulaire") String titulaire, @RequestParam("solde") double solde) {
		int id=comptes.size();
		id++;
		Compte c = new Compte(id,titulaire,solde);
		comptes.add(c);
		return "redirect:comptes";
	}
	
	@GetMapping("ajouter")
	public String ajouterCompte() {
		return "ajouter";
	}
	
	
	@RequestMapping("details/{id}")
	public String details(@PathVariable("id") int id, Model model) {
		for (Compte c:comptes) {
			if(c.getId()==id) {
				model.addAttribute("c",c);
			}
		}
		return "detailsCompte";
	}
	
	
	@RequestMapping("depot/{id}")
	public String depot (@PathVariable("id") int id, @RequestParam("montant") double montant) {
		for(Compte c:comptes) {
			if((c.getId()==id)&&(montant>0)) {
				c.setSolde(c.getSolde()+montant);
			}
		}
		return "redirect:../details/{id}";
	}
	
	@RequestMapping("retrait/{id}")
	public String retrait (@PathVariable("id") int id, @RequestParam("montant") double montant) {
		for(Compte c:comptes) {
			if((c.getId()==id)&&(c.getSolde()>montant)) {
				c.setSolde(c.getSolde()-montant);
			}
		}
		return "redirect:../details/{id}";
	}
	


}
