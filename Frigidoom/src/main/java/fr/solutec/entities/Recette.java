package fr.solutec.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
public class Recette {
	@Id @GeneratedValue
	private Long id;
	private String nom;
	private String niveau;
	private int nombrePersonne;
	private String temps;
	private String photo;
	@Column(length = 10000) //permet d'augmenter le nombre de charactères 
	private String description;
	
	@ManyToOne
	private Utilisateur utilisateur;
	
	   
}
