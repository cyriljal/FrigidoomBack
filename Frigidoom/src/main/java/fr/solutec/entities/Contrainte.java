package fr.solutec.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data

@Entity
public class Contrainte {
	@Id @GeneratedValue
	private Long id;
	private String type; //3 choix possibles : allergie, régime ou déteste
	private String intitule; //exemple : cacahuete, vegan ou brocolis
	
	@ManyToMany
	@JoinTable (name="ContrainteIngredient", joinColumns = @JoinColumn (name = "contrainte_id", referencedColumnName ="id"),
	inverseJoinColumns = @JoinColumn (name="ingredient_id", referencedColumnName = "id"))
	private List<Ingredient> ingredient; //une liste d'ingrédient sera dans une contrainte.
	
}
