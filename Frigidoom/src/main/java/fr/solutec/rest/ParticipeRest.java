package fr.solutec.rest; 
import java.util.List; 
import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.solutec.entities.Avis;
import fr.solutec.entities.Evenement;
import fr.solutec.entities.Participe;
import fr.solutec.entities.Recette;
import fr.solutec.entities.Utilisateur; 
import fr.solutec.repository.UtilisateurRepository;
import fr.solutec.repository.EvenementRepository;
import fr.solutec.repository.ParticipeRepository; 

@RestController @CrossOrigin("*") 

public class ParticipeRest { 
	
	@Autowired UtilisateurRepository utilisateurRepos; 
	@Autowired ParticipeRepository participeRepos; 
	@Autowired EvenementRepository eventRepos; 
	
	
	//afficher l'évènement et les participants de l'évènement auquel un utilisateur participe 
	
	@GetMapping("allEvent/participe/{id}") 
	public List<Participe> getAllEventByUtilisateurId (@PathVariable Long id) { 
		Optional <Utilisateur> u = utilisateurRepos.findById(id); 
		if (u.isPresent()) { 
			return participeRepos.findAllByUtilisateurId(id); 
			} else { 
				return null; 
			} 
		}
	
	@GetMapping("event/participe/{id}") 
	public ResponseEntity<List<Participe>> getEventById(@PathVariable Long id) { 
	    Optional<Evenement> optionalEvent = eventRepos.findById(id); 
	    if (optionalEvent.isPresent()) { 
	        Evenement event = optionalEvent.get();
	        List<Participe> participants = participeRepos.findAllByEvenementId(event.getId());
	        return ResponseEntity.ok(participants);
	    } else { 
	        return ResponseEntity.notFound().build();
	    } 
	}


	@GetMapping("participe/event/{id}")
	public List<Participe> accesAucInfosEvenement(@PathVariable Long id) {
		Optional <Evenement> e = eventRepos.findById(id); 
		if (e.isPresent()) { 
			return participeRepos.findAllByEvenementId(id); 
			} else { 
				return null; 
			} 
		}

	//Cette API sert à créer un évènement vide pour obtenir uniquement l'id et le créateur ensuite nous pourrons mettre à jour l'event
	@PostMapping("creation/participe/{idEvenement}/{id}")
	public Participe saveParticipe( @PathVariable Long id , @PathVariable Long idEvenement) {

		Participe p = new Participe();
		Optional<Utilisateur> u = utilisateurRepos.findById(id);
		Optional<Evenement> e = eventRepos.findById(idEvenement);
		p.setParticipant(u.orElse(null)); 
		p.setEvenement(e.orElse(null));
		p.setAccesFrigo(false);
		return participeRepos.save(p);
	}
	
	// Cette API sert à mettre à jour les participants (sauf ceux qui ont déjà accepté)
	@PutMapping("modification/participe/{idEvenement}/{id}")
	public Participe updateParticipe(@PathVariable Long id, @PathVariable Long idEvenement) {
	    Optional<Participe> existingParticipe = participeRepos.findById(id);
	    Optional<Utilisateur> u = utilisateurRepos.findById(id);
	    Optional<Evenement> e = eventRepos.findById(idEvenement);
	    if (existingParticipe.isPresent() && u.isPresent() && e.isPresent()) {
	        Participe participe = existingParticipe.get();
	        if(!participe.isAccesFrigo()){ // Vérifier si le participant n'a pas déjà accepté
	            participe.setParticipant(u.get());
	            participe.setEvenement(e.get());
	            participe.setAccesFrigo(false);
	            return participeRepos.save(participe);
	        }else{
	            // renvoyer une réponse indiquant que le participant a déjà accepté
	            return null;
	        }
	    } else {
	        return null;
	    }
	}

	
	@PostMapping("creation/createur/participe/{idEvenement}/{id}")
	public Participe saveParticipeCreateur( @PathVariable Long id , @PathVariable Long idEvenement) {
		Participe p = new Participe();
		Optional<Utilisateur> u = utilisateurRepos.findById(id);
		Optional<Evenement> e = eventRepos.findById(idEvenement);
		p.setParticipant(u.orElse(null)); 
		p.setEvenement(e.orElse(null));
		p.setAccesFrigo(true);
		return participeRepos.save(p);
	}

	// Mettre à jour créateur
	@PutMapping("modification/createur/participe/{idEvenement}/{id}")
	public Participe updateParticipeCreateur( @PathVariable Long id , @PathVariable Long idEvenement) {
		Optional<Participe> existingParticipe = participeRepos.findById(id);
		Optional<Utilisateur> u = utilisateurRepos.findById(id);
		Optional<Evenement> e = eventRepos.findById(idEvenement);
		if (existingParticipe.isPresent()) {
			Participe p = existingParticipe.get();
			p.setParticipant(u.orElse(null)); 
			p.setEvenement(e.orElse(null));
			p.setAccesFrigo(true);
			return participeRepos.save(p);
		} else {
			return null;
		}
	}

		// Modification des informations de l'événement
		@PutMapping("acceptEvenement/evenement/{idParticipe}/{idUtilisateur}")
		public Participe miseAJourParticipe(@RequestBody Participe p, @PathVariable Long idParticipe, @PathVariable Long idUtilisateur) throws NotFoundException {
		    Participe existant = participeRepos.findById(idParticipe)
		            .orElseThrow(() -> new NotFoundException());
		    
		    // Mettre à jour les informations nécessaires sur l'objet existant
		    existant.setAccesFrigo(true);
		    
		    // Enregistrer les modifications en utilisant la méthode save du repository
		    return participeRepos.save(existant);
		}


	
	
		
	//afficher l'évènement et les demandes de participation de l'évènement 
	@GetMapping("allEvent/demande/{id}") 
	public List<Participe> getAllDemandeEventByUtilisateurId (@PathVariable Long id) { 
		Optional <Utilisateur> u = utilisateurRepos.findById(id); 
		if (u.isPresent()) { return participeRepos.findAllByUtilisateurIdDemande(id); 
		} else { 
			return null; } 
		} 
	}
