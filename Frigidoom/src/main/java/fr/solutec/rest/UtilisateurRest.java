package fr.solutec.rest;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.solutec.entities.Utilisateur;
import fr.solutec.repository.UtilisateurRepository;

@RestController @CrossOrigin("*")
public class UtilisateurRest {
	
	@Autowired
	UtilisateurRepository utilisateurRepos;
	
	//Création d'une methode de connexion
	@PostMapping("connexion") 
	public Optional <Utilisateur> connectionByLoginAndMdp(@RequestBody Map<String, String> credentials) {
	    String login = credentials.get("login");
	    String mdp = credentials.get("mdp");
		return utilisateurRepos.getPersonByLoginAndMdp(login, mdp);
		}
		
	/**
	 @PostMapping ("connexion")
	 public Optional <Utilisateur> connexion (@RequestBody Utilisateur u) {
	 return utilisateurRepos.getPersonByLoginAndMdp (u.getLogin(),u.getMdp());
	 }
	 */
	 
	//Création d'une méthode d'inscription
	@PostMapping("inscription")                               
	public Utilisateur createUtilisateur(@RequestBody Utilisateur u) {
		return utilisateurRepos.save(u);
	}
	
	@GetMapping("user/{id}")
	public Optional<Utilisateur> getUtilisateurById(@PathVariable Long id) {
		return utilisateurRepos.findById(id);
	}
	
	@GetMapping("user/login/{login}")
	public Optional<Utilisateur> getUtilisateurByLogin(@PathVariable String login) {
		return utilisateurRepos.findByLogin(login);
	}
	
	

	//Mofification des informations de l'utilisateur
	@PutMapping("utilisateur/modifier/{id}")
	public Utilisateur updateUtilisateur(@RequestBody Utilisateur u, @PathVariable Long id) throws NotFoundException {
	    Utilisateur original = utilisateurRepos.findById(id)
	        .orElseThrow(() -> new NotFoundException());
	    u.setId(id);
	    u.setDateInscription(original.getDateInscription());
	    return utilisateurRepos.save(u);
	}
	
	//supprimer un compte utilisateur marche pas
	@DeleteMapping("utilisateur/supprimer/{id}")                             
	public boolean deleteUtilisateur(@PathVariable Long id) {
		Optional<Utilisateur> u=utilisateurRepos.findById(id);
		if (u.isPresent()) {                                   
			utilisateurRepos.deleteById(id);
			return true;
		}else {
			return false;
		}
	}
	
	
}
