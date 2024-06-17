package fr.solutec.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.solutec.repository.AvisRepository;
import fr.solutec.repository.RecetteRepository;
import fr.solutec.repository.UtilisateurRepository;

import fr.solutec.entities.*;

@RestController @CrossOrigin("*")
public class AvisRest {
	
	@Autowired
	AvisRepository avisRepos;
	
	@Autowired
	UtilisateurRepository utilisateurRepos;

	@Autowired
	RecetteRepository recetteRepos;

	@PostMapping("creationAvis/{id}/{idRecette}")                               
	public Avis creationAvisRecette(@RequestBody Avis avis, @PathVariable Long id,@PathVariable Long idRecette) {

		Avis a = new Avis();
		Optional<Utilisateur> u = utilisateurRepos.findById(id);
		Optional<Recette> r = recetteRepos.findById(idRecette);
		a.setUtilisateur(u.orElse(null)); 
		a.setNote(avis.getNote());
		a.setCommentaire(avis.getCommentaire());
		a.setRecette(r.orElse(null));
		return avisRepos.save(a);
	}
	
	
	@GetMapping("recette/avis/{id}")
	public List<Avis> getAvisByRecetteId(@PathVariable Long id) {
		Optional<Recette> r = recetteRepos.findById(id);
		if (r.isPresent()) {
			return avisRepos.findAvisByRecetteId(id);
		} else {
			return null;
		}
		
	}

	
	
}
