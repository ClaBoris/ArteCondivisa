package it.uniroma3.siw.model;


import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Drawing {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String name;
	@Column(length=500)
	private String description;

	@Column(length=500)
	private String image;

	//c'è una relazione molti-a-uno tra l'entità corrente e un'entità di tipo Artist
	// La specifica fetch = FetchType.EAGER indica che i dati dell'entità Artist 
	//dovrebbero essere caricati insieme ai dati dell'entità corrente senza attendere fino a quando non vengono effettivamente richiesti 
	@ManyToOne //(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Artist artist;

	//Questa annotazione indica che c'è una relazione uno-a-molti tra l'entità corrente e un insieme di entità di tipo Review
	//L'uso di fetch = FetchType.EAGER indica che i dati delle recensioni dovrebbero essere caricati insieme ai dati dell'entità corrente. 
	//cascade = CascadeType.ALL indica che le operazioni di cascata dovrebbero essere propagate anche alle recensioni associate, ad esempio, 
	//se viene eliminata l'entità corrente, tutte le recensioni associate dovrebbero essere eliminate.
	@OneToMany //(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Review> reviews;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drawing other = (Drawing) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name);
	}

}
