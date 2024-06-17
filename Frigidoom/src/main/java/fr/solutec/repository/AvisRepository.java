package fr.solutec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.solutec.entities.Avis;

public interface AvisRepository extends CrudRepository<Avis, Long>{
/**test*/
  @Query("SELECT a FROM Avis a WHERE a.recette.id = ?1")
	public List<Avis> findAvisByRecetteId(Long id);
}

