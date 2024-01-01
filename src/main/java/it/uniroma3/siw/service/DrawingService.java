package it.uniroma3.siw.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Drawing;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.DrawingRepository;


@Service
public class DrawingService {
	@Autowired
	ArtistRepository artistRepository;

	@Autowired
	DrawingRepository drawingRepository;

	

	
	 public boolean hasReviewFromAuthor(Long drawingId, String username){
		 Drawing drawing= this.drawingRepository.findById(drawingId).get();
	        Set<Review> reviews = drawing.getReviews();
	        for (Review review: reviews) {
	            if(review.getAuthor().equals(username)) {
	                return true;
	            }
	        }
	        return false;
	    }

}
