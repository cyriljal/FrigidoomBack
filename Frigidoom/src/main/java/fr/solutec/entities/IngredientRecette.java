package fr.solutec.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data

@Entity
public class IngredientRecette {
	@Id @GeneratedValue
	private Long id;
	private double quantite;
	private String unite;
	private int nombrePersonne;
	
	@ManyToOne
	private Recette recette;
	
	@ManyToOne
	private Ingredient ingredient;
	
}
