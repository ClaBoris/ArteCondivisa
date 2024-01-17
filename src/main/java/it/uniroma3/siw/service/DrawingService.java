package it.uniroma3.siw.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Drawing;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.DrawingRepository;
import jakarta.transaction.Transactional;


@Service
public class DrawingService {
	@Autowired
	ArtistRepository artistRepository;

	@Autowired
	DrawingRepository drawingRepository;



	 @Transactional
	    public boolean removeDrawing(Long drawingId) {
		        if(drawingRepository.existsById(drawingId)) {
		        	 drawingRepository.deleteById(drawingId);
		        	 return true; //ho trovato il disegno
		        }
            	return false;
	    }
	 
	 public Iterable<Drawing> getAllDrawings(){
		 return drawingRepository.findAll();
	 }

	public List<Drawing> getDrawingsByArtist(Long artistId) {
		// TODO Auto-generated method stub
		return drawingRepository.findByArtistId(artistId);
	}
	
	public void edit(Drawing drawing, Long drawingId) {
		Drawing my_drawing = this.drawingRepository.findById(drawingId).orElse(null);
		my_drawing.setName(drawing.getName());
		my_drawing.setDescription(drawing.getDescription());
		my_drawing.setImage(drawing.getImage());
		my_drawing.setArtist(drawing.getArtist());
		this.drawingRepository.save(my_drawing);
	}
}
