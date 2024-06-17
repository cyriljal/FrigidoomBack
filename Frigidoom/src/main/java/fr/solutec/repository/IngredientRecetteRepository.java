package fr.solutec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import fr.solutec.entities.IngredientRecette;


public interface IngredientRecetteRepository  extends CrudRepository<IngredientRecette, Long>{
	
	@Query("SELECT ir FROM IngredientRecette ir WHERE ir.recette.id = ?1")
	public List<IngredientRecette> findIngredientRecetteByRecetteId(Long id);


}


