package fr.solutec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.solutec.entities.Contrainte;
import fr.solutec.entities.Ingredient;

public interface ContrainteRepository  extends CrudRepository<Contrainte, Long>{
	
	@Query("SELECT i FROM Ingredient i WHERE i.type IN :types")
	List<Ingredient> getIngredientsByType(@Param("types") List<String> types);
	

}
