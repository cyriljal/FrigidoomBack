package fr.solutec.rest;

import java.util.List;
import java.util.Optional;

import javax.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import fr.solutec.entities.ListeCourse;
import fr.solutec.entities.Utilisateur;
import fr.solutec.entities.Evenement;
import fr.solutec.repository.EvenementRepository;
import fr.solutec.repository.IngredientCourseRepository;
import fr.solutec.repository.IngredientRepository;
import fr.solutec.repository.ListeCourseRepository;
import fr.solutec.repository.UtilisateurRepository;

@RestController @CrossOrigin("*")  
public class IngredientCourseRest {
	@Autowired
	IngredientCourseRepository ingredientCourseRepos;
	
	@Autowired
	ListeCourseRepository listeCourseRepos;
	
	@Autowired
	UtilisateurRepository utilisateurRepos;
	
	@Autowired
	IngredientRepository ingredientRepos;
	
	@Autowired
	EvenementRepository evenementRepos;
	
	//Afficher les ingredients de la liste de course de l'utilisateur
	@GetMapping("allIngredientCourse/utilisateur/{id}")                           
	public List<IngredientCourse> getAllByUtilisateurId (@PathVariable Long id) {
		Optional <Utilisateur> u = utilisateurRepos.findById(id);
		if (u.isPresent()) {
			return ingredientCourseRepos.findAllByUtilisateurId(id);
		} else {
			return null;
		}		
	}
	
	//Afficher les ingredients de la liste de course des participants d'un évènement
	@GetMapping("allIngredientCourse/evenement/{id}")                           
	public List<IngredientCourse> getAllIngredientCourseByEvenementId (@PathVariable Long id) {
		Optional <Evenement> e = evenementRepos.findById(id);
		if (e.isPresent()) {
			return ingredientCourseRepos.findAllIngredientCourseEvenement(id);
		} else {
			return null;
		}		
	}
	
	@GetMapping("allIngredientCourse/utilisateur/{utilisateurId}/listeCourse/{listeCourseId}")                           
	public List<IngredientCourse> getAllIngredientInListeCourse (@PathVariable Long utilisateurId, @PathVariable Long listeCourseId) {
	    Optional<Utilisateur> u = utilisateurRepos.findById(utilisateurId);
	    Optional<ListeCourse> lc = listeCourseRepos.findById(listeCourseId);
	    if (lc.isPresent() && lc.get().getUtilisateur().getId().equals(utilisateurId)) {
	        return ingredientCourseRepos.findAllByListeCourseId(listeCourseId);
	    } else {
	        return null;
	    }
	}
	@PutMapping("ingredientCourse/utilisation/true/{id}")
	public ResponseEntity<?> changeUtilisationIngredientCourse(@PathVariable Long id){
		Optional<IngredientCourse> ingredientCourseOptional = ingredientCourseRepos.findById(id);
		if (ingredientCourseOptional.isPresent()) {
	        IngredientCourse ingredientCourse = ingredientCourseOptional.get();
	        ingredientCourse.setUtilisation(true);
	        ingredientCourseRepos.save(ingredientCourse);
	        return ResponseEntity.ok().build();
		} else {
	        return ResponseEntity.notFound().build();
	    }
		
	}

	@PutMapping("ingredientCourse/utilisation/false/{id}")
	public ResponseEntity<?> changeUtilisationIngredientFalseCourse(@PathVariable Long id){
		Optional<IngredientCourse> ingredientCourseOptional = ingredientCourseRepos.findById(id);
		if (ingredientCourseOptional.isPresent()) {
	        IngredientCourse ingredientCourse = ingredientCourseOptional.get();
	        ingredientCourse.setUtilisation(false);
	        ingredientCourseRepos.save(ingredientCourse);
	        return ResponseEntity.ok().build();
		} else {
	        return ResponseEntity.notFound().build();
	    }
		
	}
	
	
	//Ajouter des ingredients
	@PostMapping("addIngredientCourse/utilisateur/{utilisateurId}/listeCourse/{listeCourseId}")
	public IngredientCourse addIngredientCourse(@RequestBody IngredientCourse ingredientCourse,
	                                             @PathVariable Long utilisateurId, @PathVariable Long listeCourseId) throws Exception {
	    Optional<Utilisateur> utilisateur = utilisateurRepos.findById(utilisateurId);
	    Optional<ListeCourse> listeCourse = listeCourseRepos.findById(listeCourseId);
	    if (utilisateur.isPresent() && listeCourse.isPresent()) {
	        if (ingredientCourse.getIngredient() != null) { // added null check
	            Optional<Ingredient> ingredient = ingredientRepos.findByNom(ingredientCourse.getIngredient().getNom());
	            if (ingredient.isPresent()) {
	                IngredientCourse ic = new IngredientCourse();
	                ic.setQuantite(ingredientCourse.getQuantite());
	                ic.setMesure(ingredientCourse.getMesure());
	                ic.setIngredient(ingredient.get());
	                ic.setListeCourse(listeCourse.get());
	                ic.setUtilisateur(utilisateur.get());
	                return ingredientCourseRepos.save(ic);
	            } else {
	                throw new Exception("L'ingrédient n'existe pas.");
	            }
	        } else {
	            throw new Exception("L'objet IngredientCourse ne contient pas d'objet Ingredient.");
	        }
	    } else {
	        throw new Exception("Vous n'êtes pas autorisé à apporter des changements.");
	    }
	}


	
	/**
	@PostMapping("addIngredientCourse/utilisateur/{utilisateurId}/listeCourse")
	public IngredientCourse addIngredientCourse(@RequestBody IngredientCourse ingredientCourse,
	                                             @PathVariable Long utilisateurId)
	                                             throws Exception {
	    Optional<Utilisateur> utilisateur = utilisateurRepos.findById(utilisateurId);
	    if ( utilisateur.isPresent()) {
	        Optional<Ingredient> ingredient = ingredientRepos.findByNom(ingredientCourse.getIngredient().getNom());
	        if (ingredient.isPresent()) {
	            IngredientCourse ic = new IngredientCourse();
	            ic.setQuantite(ingredientCourse.getQuantite());
	            ic.setMesure(ingredientCourse.getMesure());
	            ic.setIngredient(ingredient.get());
	            ic.setListeCourse(ingredientCourse.getListeCourse());
	            ic.setUtilisateur(utilisateur.get());
	            return ingredientCourseRepos.save(ic);
	        } else {
	            throw new Exception("L'ingrédient n'existe pas.");
	        }
	    } else {
	        throw new Exception("Vous n'êtes pas autorisé à apporter des changements.");
	    }
	}
*/
	
	//Modifier un ingredient (quantite, mesure etc)
	@PutMapping("/utilisateur/{utilisateurId}/modifierIngredientCourse/{listeCourseId}/{id}")
	public IngredientCourse updateIngredientCourse(@RequestBody IngredientCourse ingredientCourse, @PathVariable Long utilisateurId, @PathVariable Long listeCourseId) throws Exception {
		
		// Check if the ListCourse exists
	    Optional<ListeCourse> liste = listeCourseRepos.findById(listeCourseId);
	    IngredientCourse ic = new IngredientCourse();
	    if (liste.isPresent()) {
	    	// Check if the Utilisateur exists
	        Optional<Utilisateur> u = utilisateurRepos.findById(utilisateurId);
	        if (liste.isPresent()) {
	            // Check if the Utilisateur is the owner of the ListCourse
	            if (liste.get().getUtilisateur().getId().equals(utilisateurId)) {
	            	ic.setQuantite(ingredientCourse.getQuantite());
	        		ic.setMesure(ingredientCourse.getMesure());
	        		ic.setIngredient(ingredientCourse.getIngredient());
	        		ic.setUtilisateur(u.orElse(null));
	        		ic.setListeCourse(liste.orElse(null));
	                return ingredientCourseRepos.save(ingredientCourse);
	            } else {
	                throw new Exception("Vous n'êtes pas autorisé à apporter des changements.");
	            }
	        } else {
	            throw new Exception("Utilisateur non trouvé.");
	        }
	    } else {
	        throw new Exception("Liste de course non trouvé.");
	    }
	} 
	
	//Supprimer un ingredient de la liste
	@DeleteMapping("/utilisateur/{utilisateurId}/modifierIngredientCourse/{listeCourseId}/supprimer/{id}")
	public boolean deleteIngredientCourse (@PathVariable Long utilisateurId, @PathVariable Long listeCourseId, @PathVariable Long id) throws Exception {
		// Check if the ListCourse and IngredietnCourse exist
	    Optional<ListeCourse> liste = listeCourseRepos.findById(listeCourseId);
	    Optional<IngredientCourse> ingredientCourse = ingredientCourseRepos.findById(id);
	    if (liste.isPresent()) {
	        if (ingredientCourse.isPresent()) {
	            // Check if the Utilisateur is the owner of the ListCourse
	            if (liste.get().getUtilisateur().getId().equals(utilisateurId)) {
	            	ingredientCourseRepos.deleteById(id);
	                return true;
	            } else {
	                throw new Exception("Vous n'êtes pas autorisé à supprimer");
	            }
	        } else {
	            throw new Exception("Ingrédient non trouvé");
	        }
	    } else {
	        throw new Exception("Liste de course non trouvé.");

	}
	
}
	
	//Supprimer un ingredient du frigo
	@DeleteMapping("/utilisateur/{utilisateurId}/supprimerIngredientFrigo/supprimer/{id}")
	public boolean deleteIngredientFrigo (@PathVariable Long utilisateurId, @PathVariable Long id) throws Exception {
		// Check if the IngredietnCourse exist
	    Optional<IngredientCourse> ingredientCourse = ingredientCourseRepos.findById(id);
	        if (ingredientCourse.isPresent()) {
	            // Check if the Utilisateur is the owner of the ListCourse
	            	ingredientCourseRepos.deleteById(id);
	                return true;
	            } else {
	                throw new Exception("Vous n'êtes pas autorisé à supprimer");
	            }
	        } 

	
}
	

