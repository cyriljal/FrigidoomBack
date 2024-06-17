package fr.solutec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.solutec.entities.Participe;

public interface ParticipeRepository extends CrudRepository<Participe, Long> {

	@Query("SELECT p FROM Participe p WHERE p.participant.id = ?1 and p.accesFrigo = true")
	public List<Participe> findAllByUtilisateurId(Long utilisateurId);
	
	@Query("SELECT p FROM Participe p WHERE p.evenement.id = ?1 and p.accesFrigo = true")
	public List<Participe> findAllByEvenementId(Long id);
	
	@Query("SELECT p FROM Participe p WHERE p.participant.id = ?1 and p.accesFrigo = false")
	public List<Participe> findAllByUtilisateurIdDemande(Long utilisateurId);

	@Modifying //In this case, you can try changing the @Query annotation to use native SQL instead of HQL. 
	//Native SQL allows you to execute SQL queries that are specific to your database system, while HQL is database-independent.
	@Query(value = "DELETE FROM participe WHERE evenement_id = ?1", nativeQuery = true)
	void deleteAllByEvenementId(Long id);
	
	
}
