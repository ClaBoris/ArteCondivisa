package it.uniroma3.siw.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.ReviewValidator;
import it.uniroma3.siw.controller.validator.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Drawing;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.DrawingRepository;
import it.uniroma3.siw.repository.ReviewRepository;
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
	private ReviewRepository reviewRepository;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private DrawingService drawingService;

	@Autowired
	private ReviewValidator reviewValidator;

	@GetMapping("/")
	public String index(Model model) {
		//	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//	        UserDetails userDetails = null;
		//	        Credentials credentials = null;
		//	        if(!(authentication instanceof AnonymousAuthenticationToken)){
		//	            userDetails = (UserDetails)authentication.getPrincipal();
		//	            credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		//	        }
		//	        if(credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) return "admin/indexAdmin.html";
		//
		//	        
		//	         //model.addAttribute("userDetails", userDetails);
		//	        model.addAttribute("drawings", this.drawingRepository.findAll());
		return "index.html";
	}

	@GetMapping("/index_home")
	public String index_home(Model model){
		model.addAttribute("drawings", this.drawingRepository.findAll());
		return "index2.html";
	}

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
			return "admin/index.html";
		}
		/*model.addAttribute("userDetails", userDetails);*/
		model.addAttribute("drawings", this.drawingRepository.findAll());
		return "index2.html";
	}

	//	    @GetMapping("/index")
	//	    public String index2(Model model){
	//	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	//	        UserDetails userDetails = null;
	//	        Credentials credentials = null;
	//	        if(!(authentication instanceof AnonymousAuthenticationToken)){
	//	            userDetails = (UserDetails)authentication.getPrincipal();
	//	            credentials = credentialsService.getCredentials(userDetails.getUsername());
	//	        }
	//	        if(credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) return "admin/indexAdmin.html";
	//
	//	        model.addAttribute("userDetails", userDetails);
	//	        model.addAttribute("drawings", this.drawingRepository.findAll());
	//	        return "index.html";
	//	    }


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

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user,
			BindingResult userBindingResult, @Valid
			@ModelAttribute("credentials") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {
		this.userValidator.validate(user,userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);                        
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			userService.saveUser(user);
			model.addAttribute("user", user);
			return "formLogin.html";
		}
		return "formRegister.html";
	}

	//	    @GetMapping("/artist/{id}")
	//	    public String artist(@PathVariable("id") Long id, Model model){
	//
	//	        model.addAttribute("userDetails", this.userService.getUserDetails());
	//
	//	        Artist artist = this.artistRepository.findById(id).get();
	//	        Image profilePic = artist.getProfilePicture(); //è una string rappresentante l'immagine in base64
	//	        model.addAttribute("artist", this.artistRepository.findById(id).get());
	//	        model.addAttribute("profilePic", profilePic);
	//	        
	//	        return "artist.html";
	//	    }
	//
	//	    @GetMapping("/drawing/{id}")
	//	    public String drawing(@PathVariable("id") Long id, Model model) {
	//
	//	        UserDetails userDetails = this.userService.getUserDetails();
	//	        model.addAttribute("userDetails", userDetails);
	//
	//	        Drawing drawing= this.drawingRepository.findById(id).get();
	//	        Image image = drawing.getImage();
	//	        
	//	        model.addAttribute("drawing", drawing);
	//	        model.addAttribute("image", image);
	//	        
	//	        /* Gestione della review */
	//	        if (userDetails != null){
	//	            if(this.credentialsService.getCredentials(userDetails.getUsername()) !=null){
	//	                model.addAttribute("review", new Review());
	//	            }
	//	        }
	//
	//	        if(userDetails != null && this.credentialsService.getCredentials(userDetails.getUsername()).getRole().equals(Credentials.ADMIN_ROLE)){
	//	            model.addAttribute("admin", true);
	//	        }
	//	        return "drawing.html";
	//	    }
	//	    
	//	    @PostMapping("/user/review/{drawingId}")
	//	    public String addReview(Model model, @Valid @ModelAttribute("review") Review review, BindingResult bindingResult, @PathVariable("drawingId") Long id){
	//	        this.reviewValidator.validate(review,bindingResult);
	//	        Drawing drawing= this.drawingRepository.findById(id).get();
	//	        String username = this.userService.getUserDetails().getUsername();
	//
	//	        if(!bindingResult.hasErrors() && !this.drawingService.hasReviewFromAuthor(id, username)){
	//	            if(this.userService.getUserDetails() != null && !drawing.getReviews().contains(review)){
	//	                review.setAuthor(username);
	//	                this.reviewRepository.save(review);
	//	                drawing.getReviews().add(review);
	//	            }
	//	        }
	//	        this.drawingRepository.save(drawing);
	//
	//	        if(this.userService.getUserDetails() != null && !drawing.getReviews().contains(review)){
	//	            if(!this.drawingService.hasReviewFromAuthor(id, username)){
	//	                this.reviewRepository.save(review);
	//	                drawing.getReviews().add(review);
	//	            }
	//	            else{
	//	                model.addAttribute("reviewError", "Already Reviewed!");
	//	            }
	//
	//	        }
	//	        this.drawingRepository.save(drawing);
	//	        
	//	        model.addAttribute("drawing", drawing);
	//	        model.addAttribute("image", drawing.getImage());
	//
	//	        if(this.credentialsService.getCredentials(username).getRole().equals(Credentials.ADMIN_ROLE)){
	//	            model.addAttribute("admin", true);
	//	        }
	//	        return "drawing.html";
	//	    }
	//
	//	    @GetMapping("/admin/deleteReview/{drawingId}/{reviewId}")
	//	    public String removeReview(Model model, @PathVariable("drawingId") Long drawingId,@PathVariable("reviewId") Long reviewId){
	//	    	Drawing drawing= this.drawingRepository.findById(drawingId).get();
	//	        Review review = this.reviewRepository.findById(reviewId).get();
	//	        UserDetails userDetails = this.userService.getUserDetails();
	//
	//	        drawing.getReviews().remove(review);
	//	        this.reviewRepository.delete(review);
	//	        this.drawingRepository.save(drawing);
	//
	//	        model.addAttribute("drawing", drawing);
	//	        model.addAttribute("image", drawing.getImage());
	//
	//	        if (userDetails != null){
	//	            if(this.credentialsService.getCredentials(userDetails.getUsername()) !=null ){
	//	                model.addAttribute("review", new Review());
	//	            }
	//	            if(this.drawingService.hasReviewFromAuthor(drawingId, userDetails.getUsername())){
	//	                model.addAttribute("reviewError", "You have already reviewed this drawing.");
	//	            }
	//	            
	//	        }
	//	        return "drawing.html";
	//	    }
	@GetMapping("/formNewDrawing")
	public String formNewDrawing(Model model){
		model.addAttribute("drawing", new Drawing());
		return "formNewDrawing.html";
	}
	@PostMapping("/newDrawing")
	public String newDrawing(@ModelAttribute("drawing") Drawing drawing, Model model){
		if(!drawingRepository.existsByName(drawing.getName())){
			this.drawingRepository.save(drawing);
			model.addAttribute("drawing", drawing);
			return "drawing.html";
		}
		else{
			model.addAttribute("messaggioErrore", "Questo disegno esiste");
			return "formNewDrawing.html";
		}
	}
}
