package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.repository.ArtistRepository;

@Component
public class ArtistValidator implements Validator {
	@Autowired
    private ArtistRepository artistRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		 return Artist.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		  Artist artist = (Artist) target;
	        if(artist.getName() != null && artist.getSurname() != null
	            && this.artistRepository.existsByNameAndSurname(artist.getName(), artist.getSurname())){
	            errors.reject("artist.duplicate");
	        }
	}
	
}
