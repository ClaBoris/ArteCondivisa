package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.ArtistValidator;
import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.UserValidator;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Drawing;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.DrawingRepository;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.DrawingService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class GlobalController {
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private DrawingRepository drawingRepository;

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ArtistValidator artistValidator;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private DrawingService drawingService;


	@Autowired
	private ArtistService artistService;

				/***GENERALI***/

	@GetMapping("/")
	public String index(Model model) {
		return "index.html";
	}

	@GetMapping("/index_home")
	public String index_home(Model model){
		UserDetails userDetails = this.userService.getUserDetails();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		if(credentials!= null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			model.addAttribute("drawings", this.drawingRepository.findAll());
			return "index2.html";
		}else {
			model.addAttribute("drawings", this.drawingRepository.findAll());
			return "user_home.html";
		}
	}

	@GetMapping("/user_home")
	public String user_home(Model model){
		model.addAttribute("drawings", this.drawingRepository.findAll());
		return "user_home.html";
	}
	
	
				/***GENERALI***/
	
	@GetMapping("/success")
	public String index2(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = null;
		Credentials credentials = null;
		if(!(authentication instanceof AnonymousAuthenticationToken)){
			userDetails = (UserDetails)authentication.getPrincipal();
			credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		}
		if(credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			model.addAttribute("drawings", this.drawingRepository.findAll());
			return "index2.html";
		}
		model.addAttribute("drawings", this.drawingRepository.findAll());
		return "user_home.html";
	}


	@GetMapping(value = "/login")
	public String showLoginForm (Model model) {
		return "formLogin.html";
	}

	@GetMapping(value = "/register")
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegister.html";
	}

	/* ridondante, ma viene usata anche per la admin dashboard */
	@GetMapping("/drawings")
	public String drawings(Model model){
		model.addAttribute("drawings", this.drawingRepository.findAll());
		return "index.html";
	}

	@GetMapping("/artists")
	public String artists(Model model){
		model.addAttribute("artists", this.artistRepository.findAll());
		return "artists.html";
	}

	@GetMapping("/showArtistsForUser")
	public String showArtistsForUser(Model model){
		model.addAttribute("artists", this.artistRepository.findAll());
		return "artistsForUser.html";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult userBindingResult, @Valid @ModelAttribute("credentials") Credentials credentials,
			BindingResult credentialsBindingResult, Model model) {
		this.userValidator.validate(user,userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);                        
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			userService.saveUser(user);
			model.addAttribute("user", user);
			return "formLogin.html";
		}else
		{
			if(userBindingResult.hasErrors()){
				model.addAttribute("registrationErrorUser", "Mail già in uso");
			}
			model.addAttribute("registrationError", "Username già in uso");
		}
		return "formRegister.html";
	}

	@GetMapping("/formNewDrawing")
	public String formNewDrawing(Model model){
		model.addAttribute("drawing", new Drawing());
		model.addAttribute("artists", this.artistRepository.findAll());
		return "formNewDrawing.html";
	}
	@PostMapping("/newDrawing")
	public String newDrawing(@ModelAttribute("drawing") Drawing drawing,  Model model){
		if(!drawingRepository.existsByName(drawing.getName())){
			this.drawingRepository.save(drawing);
			model.addAttribute("drawing", drawing);
			return "drawing.html";
		}
		else{
			return "formNewDrawing.html";
		}
	}


	@PostMapping("/newDrawing/{id}")
	public String updateDrawing(@ModelAttribute("drawing") Drawing drawing,@PathVariable("id") Long drawingId,  Model model){
		this.drawingService.edit(drawing, drawingId);
		model.addAttribute("drawings", this.drawingRepository.findAll());
		return "index2.html";
	}

	@GetMapping("/formUpdateDrawing/{id}")
	public String formUpdateDrawing(Model model, @PathVariable("id") Long drawingId){
		model.addAttribute("drawing", this.drawingRepository.findById(drawingId).get());
		model.addAttribute("artists", this.artistRepository.findAll());
		return "formUpdateDrawing.html";
	}
	@GetMapping("/formNewArtist")
	public String formNewArtist(Model model){
		model.addAttribute("artist",new Artist());
		return "formNewArtist.html";
	}

	@PostMapping("/newArtist")
	public String newArtist(@ModelAttribute("artist") Artist artist,BindingResult bindingResult, Model model){
		artistValidator.validate(artist, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.artistService.save(artist);
			model.addAttribute("artists", this.artistRepository.findAll());
			return "artists.html";
		}else {
			return "formNewArtist.html";
		}
	}

	@GetMapping("/artist/{artistId}/drawings")
	public String getDrawingsByArtist(@PathVariable Long artistId, Model model) {
		// Verifica se l'artista esiste
		if (artistRepository.existsById(artistId)) {
			// Ottieni i disegni dell'artista
			List<Drawing> drawings = drawingService.getDrawingsByArtist(artistId);

			// Aggiungi i dettagli dell'artista e i disegni al modello
			model.addAttribute("artistId", artistId);
			model.addAttribute("artist_drawings", drawings);

			// Restituisci il nome della vista HTML
			return "artist_drawings.html";
		} else {
			// Gestisci il caso in cui l'artista non esiste, ad esempio reindirizzando a una pagina di errore
			return "artists.html";
		}
	}

}
