package fr.solutec.rest;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Sides;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.MethodInfoTransformer;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.solutec.entities.Evenement;
import fr.solutec.entities.IngredientCourse;
import fr.solutec.entities.ListeCourse;
import fr.solutec.entities.Participe;
import fr.solutec.entities.Utilisateur;
import fr.solutec.repository.EvenementRepository;
import fr.solutec.repository.ParticipeRepository;
import fr.solutec.repository.UtilisateurRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher.IsConstructor;

@RestController @CrossOrigin("*")
public class EvenementRest {
	@Autowired
	private EvenementRepository eventRepos;
	@Autowired
	UtilisateurRepository utilisateurRepos;
	@Autowired
	private ParticipeRepository participeRepos;
	/*
	@PostMapping("saveEvent/evenement/{id}")
	public Evenement creationAvecInfos(@RequestBody Evenement e, @PathVariable Long id) throws NotFoundException {
		Evenement e = eventRepos.findById(id);
			.orElseThrow(() -> new NotFoundException());
		return eventRepos.save(e);
	}
	
*/
	//Mofification des informations de l'evenement 
	@PutMapping("saveEvent/evenement/{id}/{idCreateur}") 
	public Evenement creationAvecInfos(@RequestBody Evenement e, @PathVariable Long id,@PathVariable Long idCreateur) { 
		Optional<Utilisateur> u = utilisateurRepos.findById(idCreateur); 
		e.setOrganisateur(u.orElse(null)); 
		e.setId(id); 
		return eventRepos.save(e); }
	
	//Mofification des informations de l'evenement
	@PutMapping("updateEvent/evenement/{id}/{idCreateur}")
	public Evenement updateAvecInfos(@RequestBody Evenement e, @PathVariable Long id,@PathVariable Long idCreateur) {
		Optional<Utilisateur> u = utilisateurRepos.findById(idCreateur);
		e.setOrganisateur(u.orElse(null));
		e.setId(id);
	return eventRepos.save(e);
	}
	
	//Cette API sert à créer un évènement vide pour obtenir uniquement l'id et le créateur ensuite nous pourrons mettre à jour l'event
	@PostMapping("saveEvent/test/evenement/{id}")
	public Evenement saveEvent( @PathVariable Long id) {
		Optional<Utilisateur> u = utilisateurRepos.findById(id);
		Evenement e = new Evenement();
		e.setOrganisateur(u.orElse(null));
		return eventRepos.save(e);
	}
	
	@PutMapping("updateEvent/test/evenement/{id}")
	public Evenement updateEvent(@PathVariable Long id) {
	    Optional<Utilisateur> u = utilisateurRepos.findById(id);
	    Optional<Evenement> existingEvent = eventRepos.findById(id);
	    if (existingEvent.isPresent()) {
	        Evenement e = existingEvent.get();
	        e.setOrganisateur(u.orElse(null));
	        return eventRepos.save(e);
	    } else {
	        return null;
	    }
	}


	@GetMapping("event/organisateur/{id}")
	public Optional<Utilisateur> getOrganisateur(@PathVariable Long id) {
		    return eventRepos.getOrganisateur(id);
		
	}	
	
	//vérifie si l'event existe et si elle existe alors il faut d'abord supprimer tous les participants de l'event
	//puis supprimer l'event
	@Transactional //This annotation will ensure that the method is executed in a transaction with write permissions.
	@DeleteMapping("/event/supprimer/{id}")
	public boolean deleteEvenement(@PathVariable Long id) {
	    Optional<Evenement> existingEvent = eventRepos.findById(id);
	    if (existingEvent.isPresent()) {
	        // delete participants
	        participeRepos.deleteAllByEvenementId(id);
	        // delete event
	        eventRepos.delete(existingEvent.get());
	        return true;
	    }
	    return false;
	}




}
