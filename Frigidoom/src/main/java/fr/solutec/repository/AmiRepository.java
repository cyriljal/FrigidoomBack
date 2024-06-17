package fr.solutec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.solutec.entities.Ami;
import fr.solutec.entities.Participe;
import fr.solutec.entities.Utilisateur;

public interface AmiRepository extends CrudRepository<Ami, Long> {
	
	@Query("SELECT a FROM Ami a WHERE (a.idDemandeur.id = ?1 OR a.idReceveur.id = ?1) AND accepte=true")
	List<Ami> getMesAmis(Long id);
	
	
	@Query("SELECT a FROM Ami a WHERE (a.idDemandeur.id = ?1 OR a.idReceveur.id = ?1) AND accepte=false")
	List<Ami> getMesDemandeAmis(Long id);
	
	@Query("SELECT a FROM Ami a WHERE (a.idDemandeur.id = ?1 OR a.idReceveur.id = ?1) and a.accepte = false")
	public List<Ami> findAllAmiByUtilisateurIdDemande(Long utilisateurId);

	@Query ("SELECT a.idReceveur FROM Ami a WHERE a.id = ?1 AND a.accepte = false")
	public Optional<Utilisateur> getDemandeur (Long id);
}
