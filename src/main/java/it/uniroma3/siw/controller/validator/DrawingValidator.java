package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Drawing;
import it.uniroma3.siw.repository.DrawingRepository;

@Component

public class DrawingValidator  implements Validator{
	@Autowired
	private DrawingRepository drawingRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		 return Drawing.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		   Drawing drawing= (Drawing) target;
	        if(drawing.getName() != null && drawingRepository.existsByName(drawing.getName())){
	            errors.reject("movie.duplicate");
	        }
	}
}
