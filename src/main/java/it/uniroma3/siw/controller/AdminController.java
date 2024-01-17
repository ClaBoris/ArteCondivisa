package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.DrawingService;

@Controller
public class AdminController {

	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private DrawingService drawingService;
	@Autowired
	private ArtistService artistService;
	

	@GetMapping("/admin/removeDrawing/{drawingId}")
	public String removeDrawing (@PathVariable("drawingId") Long drawingId, Model model) {
		drawingService.removeDrawing(drawingId);
		model.addAttribute("drawings", drawingService.getAllDrawings());
		return "index2.html";
	}
		
	
	@GetMapping("/admin/removeArtistAndDrawings/{artistId}")
	public String removeArtistAndDrawings(@PathVariable Long artistId, Model model) {
	    // Codice per eliminare l'artista e i suoi disegni
	    artistService.removeArtistAndDrawings(artistId);
	    model.addAttribute("artists", this.artistRepository.findAll());
	    return "artists.html";
	}
	
}
