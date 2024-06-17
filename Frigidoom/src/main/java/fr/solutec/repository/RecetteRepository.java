package fr.solutec.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.solutec.entities.Recette;

import org.springframework.data.repository.CrudRepository; 

public interface RecetteRepository extends CrudRepository<Recette, Long> { 
	
	@Query ("SELECT r from Recette r join IngredientRecette ir on r.id=ir.recette.id " 
			+ "join IngredientCourse ic on ir.ingredient.id = ic.ingredient.id AND ic.disponible = true AND ic.utilisateur.id = ?1 " 
			+ "join Ingredient i on i.id = ic.ingredient.id")
	public List<Recette> findRecettesByIngredientCourse(@Param("userId") Long Id);


	@Query ("SELECT DISTINCT r from Recette r join IngredientRecette ir on r.id=ir.recette.id " 
		+ "join IngredientCourse ic on ic.utilisation = true AND ic.disponible = true AND ic.utilisateur.id = ?1"
		+ " AND ic.ingredient.id = ir.ingredient.id " 
		)
	public List<Recette> findRecettesByIngredientCourseUtilisation(@Param("userId") Long Id);


	@Query("SELECT r FROM Recette r WHERE r.utilisateur.id = ?1")
	public List<Recette> findRecettesByUserId(Long id);
	
	//recette pour un evenement
	@Query ("SELECT r from Recette r "
			+ "JOIN IngredientRecette ir ON r.id = ir.recette.id "
			+ "JOIN IngredientCourse ic ON ir.ingredient.id = ic.ingredient.id "
			+ "JOIN ListeCourse lc ON ic.listeCourse.id = lc.id "
			+ "JOIN Utilisateur u ON lc.utilisateur.id = u.id "
			+ "JOIN Participe p ON u.id = p.participant.id "
			+ "WHERE p.evenement.id = ?1 AND ic.disponible = true AND ic.utilisation = true")
	public List<Recette> findRecettesForEventByEventId(Long id);
}
