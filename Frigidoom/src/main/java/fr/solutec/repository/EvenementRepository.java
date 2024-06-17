package fr.solutec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.solutec.entities.Evenement;

import fr.solutec.entities.Utilisateur;


public interface EvenementRepository  extends CrudRepository<Evenement, Long>{
	
	@Query ("SELECT e.organisateur FROM Evenement e WHERE e.id = ?1")
	public Optional<Utilisateur> getOrganisateur (Long id);
}
