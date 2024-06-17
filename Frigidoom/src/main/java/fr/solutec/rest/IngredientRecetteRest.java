package fr.solutec.rest;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.solutec.entities.Ingredient;
import fr.solutec.entities.IngredientCourse;
import fr.solutec.entities.IngredientRecette;
import fr.solutec.entities.ListeCourse;
import fr.solutec.entities.Recette;
import fr.solutec.entities.Utilisateur;
import fr.solutec.repository.IngredientRecetteRepository;
import fr.solutec.repository.IngredientRepository;
import fr.solutec.repository.RecetteRepository;
import fr.solutec.repository.UtilisateurRepository;
@RestController @CrossOrigin("*")
public class IngredientRecetteRest {

	@Autowired
	RecetteRepository recetteRepos;
	@Autowired
	IngredientRecetteRepository ingredientRecetteRepos;
	@Autowired
	UtilisateurRepository utilisateurRepos;
	@Autowired
	IngredientRepository ingredientRepos;


	
	@GetMapping("recette/ingredientRecette/{id}")                           
	public List<IngredientRecette> getIngredientRecetteByRecetteId(@PathVariable Long id) {
		Optional<Recette> r = recetteRepos.findById(id);
		if (r.isPresent()) {
			return ingredientRecetteRepos.findIngredientRecetteByRecetteId(id);
		} else {
			return null;
		}		
	}
	
	@PostMapping("utilisateur/{utilisateurId}/recettes/{recetteId}/ingredients")
	public IngredientRecette ajouterIngredientsARecette(@PathVariable Long recetteId, @PathVariable Long utilisateurId, @RequestBody IngredientRecette ingredientRecette) {
	    Optional<Utilisateur> optionalUtilisateur = utilisateurRepos.findById(utilisateurId);
	    Optional<Recette> optionalRecette = recetteRepos.findById(recetteId);
	    if (optionalUtilisateur.isPresent() && optionalRecette.isPresent()) {
	        Optional<Ingredient> optionalIngredient = ingredientRepos.findByNom(ingredientRecette.getIngredient().getNom());
	        if (optionalIngredient.isPresent()) {
	            IngredientRecette ir = new IngredientRecette();
	            ir.setQuantite(ingredientRecette.getQuantite());
	            ir.setUnite(ingredientRecette.getUnite());
	            ir.setNombrePersonne(optionalRecette.get().getNombrePersonne());
	            ir.setIngredient(optionalIngredient.get());
	            ir.setRecette(optionalRecette.get());
	            return ingredientRecetteRepos.save(ir);
	        }
	    }
	    return null;
	}

	//Supprimer un ingredient de la liste
	@DeleteMapping("/utilisateur/{utilisateurId}/modifierIngredientRecette/{recetteId}/supprimer/{id}")
	public boolean deleteIngredientRecette(@PathVariable Long utilisateurId, @PathVariable Long recetteId, @PathVariable Long id) throws Exception {
	    // Check if the Recette and IngredientRecette exist
	    Optional<Recette> optionalRecette = recetteRepos.findById(recetteId);
	    Optional<IngredientRecette> optionalIngredientRecette = ingredientRecetteRepos.findById(id);
	    if (optionalRecette.isPresent()) {
	        if (optionalIngredientRecette.isPresent()) {
	            // Check if the Utilisateur is the owner of the Recette
	            if (optionalRecette.get().getUtilisateur().getId().equals(utilisateurId)) {
	                ingredientRecetteRepos.deleteById(id);
	                return true;
	            } else {
	                throw new Exception("Vous n'êtes pas autorisé à supprimer cet ingrédient de la recette");
	            }
	        } else {
	            throw new Exception("Ingrédient non trouvé");
	        }
	    } else {
	        throw new Exception("Recette non trouvée.");
	    }
	}



	}


