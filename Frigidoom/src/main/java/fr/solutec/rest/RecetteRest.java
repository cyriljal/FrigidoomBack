package fr.solutec.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.solutec.entities.Recette;
import fr.solutec.entities.Utilisateur;
import fr.solutec.repository.RecetteRepository;
import fr.solutec.repository.UtilisateurRepository;

@RestController @CrossOrigin("*")
public class RecetteRest {
	
	@Autowired
	RecetteRepository recetteRepos;
	
	@Autowired
	UtilisateurRepository utilisateurRepos;
	
	//Trouver une recette en fonction de l'id de l'utilisateur et ce qu'il a dans son frigo
	@GetMapping ("recette/{id}")
	public List<Recette> searchRecette (@PathVariable Long id) {
		Optional <Utilisateur> u = utilisateurRepos.findById(id);
		if (u.isPresent()) {
			return recetteRepos.findRecettesByIngredientCourse(id);
		} else {
			return null;
		}		
	}
	
	
	//Trouver une recette en fonction de l'id de l'utilisateur et ce qu'il a dans son frigo
		@GetMapping ("recette/utilisation/{id}")
		public List<Recette> searchRecetteByIngredientSouhaité (@PathVariable Long id) {
			Optional <Utilisateur> u = utilisateurRepos.findById(id);
			if (u.isPresent()) {
				return recetteRepos.findRecettesByIngredientCourseUtilisation(id);
			} else {
				return null;
			}		
		}
		
	@GetMapping("allrecette")                              
	public Iterable <Recette> getAllRecette() { 
		return recetteRepos.findAll();                   
	}
	
	@PostMapping("creationRecette/{id}")                               
	public Recette createRecette(@RequestBody Recette recette, @PathVariable Long id) {

	    Recette r = new Recette();
	    Optional<Utilisateur> u = utilisateurRepos.findById(id);
	    r.setUtilisateur(u.orElse(null)); 
	    r.setNom(recette.getNom());
	    r.setNombrePersonne(recette.getNombrePersonne());
	    r.setNiveau(recette.getNiveau());
	    r.setPhoto(recette.getPhoto());
	    r.setTemps(recette.getTemps());
	    r.setDescription(recette.getDescription()); // Ajout de cette ligne pour assigner la valeur pour description
	    return recetteRepos.save(r);
	}
	
	@PutMapping("modifierRecette/{id}")
	public Recette updateRecette(@RequestBody Recette recette, @PathVariable Long id) {
	    Optional<Recette> optionalRecette = recetteRepos.findById(id);
	    if (optionalRecette.isPresent()) {
	        Recette r = optionalRecette.get();
	        r.setNom(recette.getNom());
	        r.setNombrePersonne(recette.getNombrePersonne());
	        r.setNiveau(recette.getNiveau());
	        r.setPhoto(recette.getPhoto());
	        r.setTemps(recette.getTemps());
	        r.setDescription(recette.getDescription()); // Ajout de cette ligne pour assigner la valeur pour description
	        return recetteRepos.save(r);
	    } else {
	        return null;
	    }
	}


	@GetMapping("inforecette/{id}")                              
	public Optional<Recette> getById(@PathVariable Long id) { 
		return recetteRepos.findById(id);                   
	}
	
	@GetMapping("recette/utilisateur/{id}")
	public List<Recette> searchRecetteByUstilisateurId (@PathVariable Long id){
		Optional <Utilisateur> u = utilisateurRepos.findById(id);
		if (u.isPresent()) {
			return recetteRepos.findRecettesByUserId(id);
		} else {
			return null;
		}		
	}
}
