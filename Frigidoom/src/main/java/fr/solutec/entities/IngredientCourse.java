package fr.solutec.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data

@Entity
public class IngredientCourse {
	@Id @GeneratedValue
	private Long id;
	private double quantite;
	private String mesure;
	private boolean disponible = false;
	private boolean utilisation = false;
	
	
	@ManyToOne
	private ListeCourse listeCourse;
	
	@ManyToOne
	private Ingredient ingredient;
	
	@ManyToOne
	private Utilisateur utilisateur;
	
}
