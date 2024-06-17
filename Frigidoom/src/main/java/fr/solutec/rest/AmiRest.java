package fr.solutec.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

import fr.solutec.entities.Ami;
import fr.solutec.entities.Participe;
import fr.solutec.entities.Utilisateur;
import fr.solutec.repository.AmiRepository;
import fr.solutec.repository.UtilisateurRepository;

@RestController @CrossOrigin("*")
public class AmiRest {
	@Autowired
	private AmiRepository amiRepos;
	@Autowired
	UtilisateurRepository utilisateurRepos;
	
	
	@PostMapping("saveAmitie/ami/{id}")
	public Ami saveAmitie(@RequestBody Ami a,@PathVariable Utilisateur id) {
		a.setIdDemandeur(id);
		return amiRepos.save(a);
	}
	
	@PostMapping("saveAmitie/ami/{id}/{idReceveur}")
	public Ami saveAmitieWithId(@RequestBody Ami a,@PathVariable Utilisateur id,@PathVariable Utilisateur idReceveur) {
		a.setIdDemandeur(id);
		a.setIdReceveur(idReceveur);
		return amiRepos.save(a);
	}

	// Modification des informations de l'événement
	@PutMapping("acceptAmi/ami/{idAmi}/{idUtilisateur}")
	public Ami miseAJourParticipe( @PathVariable Long idAmi, @PathVariable Long idUtilisateur) throws NotFoundException {
	    Ami existant = amiRepos.findById(idAmi)
	            .orElseThrow(() -> new NotFoundException());
	    
	    // Mettre à jour les informations nécessaires sur l'objet existant
	    existant.setAccepte(true);
	    
	    // Enregistrer les modifications en utilisant la méthode save du repository
	    return amiRepos.save(existant);
	}


	@GetMapping("ami/demandeur/{id}")
	public Optional<Utilisateur> getDemandeur(@PathVariable Long id) {
		Optional<Ami> a = amiRepos.findById(id);
		if (a.isPresent()) {
			return amiRepos.getDemandeur(id);
		}else {
			return null;
		}  
		
		
	}
	
	
	@DeleteMapping("supprimerAmi/ami/{id}")
	public boolean refuserAmi (@PathVariable Long id) {
		Optional<Ami> a = amiRepos.findById(id);
		if (a.isPresent()) {
			amiRepos.deleteById(id);
			return true;
		}else {
			return false;
		}
	}
	@GetMapping("ami/objet/{id}")                             //URL qui declenche la méthode en mettant dans API méthode GET
	public List<Ami> getAmiObject(@PathVariable Long id) {
		return amiRepos.getMesAmis(id);                 
	}
	
	
	
	@GetMapping("mesAmis/ami/{id}")
	public List<Utilisateur> mesAmis(@PathVariable Long id) {
		List<Utilisateur> amis = new ArrayList<>();
		List<Ami> recup = amiRepos.getMesAmis(id);
		Optional<Utilisateur> u = utilisateurRepos.findById(id);
		if (u.isPresent()) {
			for (Ami ami : recup) {
				if (!ami.getIdDemandeur().equals(u.get())) {
					amis.add(ami.getIdDemandeur());
				}
				if (!ami.getIdReceveur().equals(u.get())) {
					amis.add(ami.getIdReceveur());
				}
			}
		}
		return amis;
		
	}
	
	
		@GetMapping("allAmi/demande/{id}") 
		public List<Ami> getAllDemandeEventByUtilisateurId (@PathVariable Long id) { 
			Optional <Utilisateur> u = utilisateurRepos.findById(id); 
			if (u.isPresent()) { 
				return amiRepos.findAllAmiByUtilisateurIdDemande(id);
				}else {
					return null;
				}
		}	
}
