package fr.solutec.rest;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

import fr.solutec.entities.IngredientCourse;
import fr.solutec.entities.ListeCourse;
import fr.solutec.entities.Utilisateur;
import fr.solutec.repository.IngredientCourseRepository;
import fr.solutec.repository.ListeCourseRepository;
import fr.solutec.repository.UtilisateurRepository;

@RestController @CrossOrigin("*")
public class ListeCourseRest {
	@Autowired
	ListeCourseRepository listeCourseRepos;
	
	@Autowired
	UtilisateurRepository utilisateurRepos;
	
	@Autowired
	IngredientCourseRepository ingredientCourseRepos;

	//Afficher liste de course par utilisateur
	@GetMapping("allListeCourse/utilisateur/{id}")                           
	public List<ListeCourse> getAllByUtilisateurId (@PathVariable Long id) {
		Optional <Utilisateur> u = utilisateurRepos.findById(id);
		if (u.isPresent()) {
			return listeCourseRepos.findAllByUtilisateurId(id);
		} else {
			return null;
		}		
	}
	
	//Afficher liste de course  archivé de l'utilisateur
	@GetMapping("allListeCourseArchive/utilisateur/{id}")                           
	public List<ListeCourse> getAllArchiveByUtilisateurId (@PathVariable Long id) {
		Optional <Utilisateur> u = utilisateurRepos.findById(id);
		if (u.isPresent()) {
			return listeCourseRepos.findAllArchiveByUtilisateurId(id);
		} else {
			return null;
		}		
	}
	
	@PostMapping("createListeCourse/utilisateur/{id}")                               
	public ListeCourse createListeCourse(@RequestBody Map<String, String> requestMap, @PathVariable Long id) {
	    String titre = requestMap.get("titre");
	    Optional<Utilisateur> utilisateur = utilisateurRepos.findById(id);
	    ListeCourse lc = new ListeCourse();
	    lc.setTitre(titre);
	    lc.setUtilisateur(utilisateur.orElse(null));
	    return listeCourseRepos.save(lc);
	}

	
	//Modifier une liste de course par l'auteur
	
	@PutMapping("/utilisateur/{utilisateurId}/modifierListeCourse/{listeCourseId}")
	public ListeCourse updateListeCourse(@RequestBody ListeCourse listeCourse, @PathVariable Long utilisateurId, @PathVariable Long listeCourseId) throws Exception {
	    // Retrieve the ListCourse from the repository
	    Optional<ListeCourse> liste = listeCourseRepos.findById(listeCourseId);
	    // Check if the ListCourse exists
	    if (liste.isPresent()) {
	        // Retrieve the Utilisateur from the repository
	        Optional<Utilisateur> u = utilisateurRepos.findById(utilisateurId);
	        // Check if the Utilisateur exists
	        if (liste.isPresent()) {
	            // Check if the Utilisateur is the owner of the ListCourse
	            if (liste.get().getUtilisateur().getId().equals(utilisateurId)) {
	                // Set the id of the ListCourse to update
	                listeCourse.setId(listeCourseId);
	                // Set the Utilisateur of the ListCourse to the retrieved Utilisateur
	                listeCourse.setUtilisateur(u.get());
	                //Set the dateCreation of the ListCourse to the original dateCreation
	                listeCourse.setDateCreation(liste.get().getDateCreation());
	                //Set the dateModification to now
	                listeCourse.setDateModification(LocalDate.now());
	                // Save the updated ListCourse to the repository
	                return listeCourseRepos.save(listeCourse);
	            } else {
	                throw new Exception("Vous n'êtes pas autorisé à apporter des changements à cette liste de course.");
	            }
	        } else {
	            throw new Exception("Utilisateur non trouvé.");
	        }
	    } else {
	        throw new Exception("Liste de course non trouvé.");
	    }
	}

	//supprimer une liste de course 
	@DeleteMapping("utilisateur/{utilisateurId}/supprimerListeCourse/{listeCourseId}")                             
	public boolean deleteListeCourse(@PathVariable Long utilisateurId, @PathVariable Long listeCourseId) throws Exception {
	    // Retrieve the ListCourse from the repository
	    Optional<ListeCourse> liste = listeCourseRepos.findById(listeCourseId);
	    // Check if the ListCourse exists
	    if (liste.isPresent()) {
	        // Retrieve the Utilisateur from the repository
	        Optional<Utilisateur> u = utilisateurRepos.findById(utilisateurId);
	        // Check if the Utilisateur exists
	        if (u.isPresent()) {
	            // Check if the Utilisateur is the owner of the ListCourse
	            if (liste.get().getUtilisateur().getId().equals(utilisateurId)) {
	                listeCourseRepos.deleteById(listeCourseId);
	               // listeCourseRepos.deleteAllFromListeCourse(listeCourseId) ;
	                // Save the updated ListCourse to the repository
	                return true;
	            } else {
	                throw new Exception("Vous n'êtes pas autorisé à supprimer cette liste de course.");
	            }
	        } else {
	            throw new Exception("Utilisateur non trouvé.");
	        }
	    } else {
	        throw new Exception("Liste de course non trouvée.");
	    }
	}
	
	//Valider une liste de course
	@PutMapping("/listeCourse/{listeCourseId}/valider")
	public void validerListeCourse(@PathVariable Long listeCourseId) throws Exception {
	    Optional<ListeCourse> optionalListeCourse = listeCourseRepos.findById(listeCourseId);
	    if (optionalListeCourse.isPresent()) {
	        ListeCourse listeCourse = optionalListeCourse.get();
	        if (!listeCourse.isValide()) {
	            List<IngredientCourse> ingredientCourses = ingredientCourseRepos.findAllByListeCourseId(listeCourseId);
	            for (IngredientCourse ingredientCourse : ingredientCourses) {
	                ingredientCourse.setDisponible(true);
	                ingredientCourseRepos.save(ingredientCourse);
	            }
	            listeCourse.setValide(true);
	            listeCourseRepos.save(listeCourse);
	        } else {
	            throw new Exception("La liste de course a déjà été validée.");
	        }
	    } else {
	        throw new Exception("Liste de course non trouvée.");
	    }
	}

	
	@DeleteMapping("utilisateur/{utilisateurId}/listeCourse/{listeCourseId}/supprimerTousLesIngredients")
	public boolean deleteAllIngredientsFromListeCourse(@PathVariable Long utilisateurId, @PathVariable Long listeCourseId) throws Exception {
	    Optional<ListeCourse> liste = listeCourseRepos.findById(listeCourseId);
	    if (liste.isPresent()) {
	        Optional<Utilisateur> u = utilisateurRepos.findById(utilisateurId);
	        if (u.isPresent()) {
	            if (liste.get().getUtilisateur().getId().equals(utilisateurId)) {
	                List<IngredientCourse> ingredients = ingredientCourseRepos.findAllByListeCourseId(listeCourseId);
	                for (IngredientCourse ingredient : ingredients) {
	                    ingredientCourseRepos.delete(ingredient);
	                }
	                listeCourseRepos.delete(liste.get());
	                return true;
	            } else {
	                throw new Exception("Vous n'êtes pas autorisé à supprimer les ingrédients de cette liste de course.");
	            }
	        } else {
	            throw new Exception("Utilisateur non trouvé.");
	        }
	    } else {
	        throw new Exception("Liste de course non trouvée.");
	    }
	}

	

}
