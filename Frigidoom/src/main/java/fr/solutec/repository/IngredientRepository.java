package fr.solutec.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import fr.solutec.entities.Ingredient;


public interface IngredientRepository  extends CrudRepository<Ingredient, Long> {

	Optional<Ingredient> findByNom(String nom);


}
