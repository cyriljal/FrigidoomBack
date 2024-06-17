package fr.solutec.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//ajout constructeur vide, constructeur plein, getter et setter
@NoArgsConstructor @AllArgsConstructor @Data

//créer classe dans SQL
@Entity 
public class Utilisateur {
	@Id @GeneratedValue
	private Long id;
	private String nom;
	private String prenom;
	@Column(unique=true)
	private String login;
	@Column(unique=true)
	private String mail;
	private String mdp;
	private String adresse;
	private LocalDate dateNaissance;
	private LocalDate dateInscription;
	
	@ManyToMany
	@JoinTable (name="ContrainteUtilisateur", joinColumns = @JoinColumn (name = "utilisateur_id", referencedColumnName ="id"),
	inverseJoinColumns = @JoinColumn (name="contrainte_id", referencedColumnName = "id"))
	private List<Contrainte> contrainte; //un utilisateur aura une liste de contrainte
	
	
	@PrePersist
	protected void onCreate() {
	    this.dateInscription = LocalDate.now(); // permet de mettre par défaut la date d'inscription à aujourd'hui
	}
	
	
    public String encrypt(String mdp){
        String crypte="";
        for (int i=0; i<mdp.length();i++)  {
            int c=mdp.charAt(i)^48; 
            crypte=crypte+(char)c;
        }
        return crypte;
    }





 
	
}

