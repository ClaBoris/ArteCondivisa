package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
//	   @Autowired
//	    private ImageRepository imageRepository;
//	    @Autowired
//	    private DrawingRepository drawingRepository;
//	    @Autowired
//	    private ArtistRepository artistRepository;
//	    @Autowired
//	    private DrawingValidator drawingValidator;
//	    @Autowired
//	    private ArtistValidator artistValidator;
//	    @Autowired
//	    private DrawingService drawingService;
//	    @Autowired
//	    private UserService userService;
//	    
//	    
//	    @GetMapping("/admin/formNewDrawing")
//	    public String newDrawing(Model model){
//	        model.addAttribute("drawing",new Drawing());
//	        return "/admin/formNewDrawing.html";
//	    }
//
//	    @PostMapping("/admin/uploadDrawing")
//	    public String newDrawing(Model model, @Valid @ModelAttribute("drawing") Drawing drawing, BindingResult bindingResult, @RequestParam("file") MultipartFile image) throws IOException {
//	        this.drawingValidator.validate(drawing,bindingResult);
//	        if(!bindingResult.hasErrors()){
//	            this.drawingService.createDrawing(drawing, image);
//	            model.addAttribute("drawing", drawing);
//	            model.addAttribute("image", drawing.getImage());
//	            model.addAttribute("userDetails", this.userService.getUserDetails());
//	            return "drawing.html";
//	        } else {
//	            return "/admin/formNewDrawing.html";
//	        }
//	    }
//
//	    @GetMapping("/admin/formNewArtist")
//	    public String formNewArtist(Model model){
//	        model.addAttribute("artist",new Artist());
//	        return "/admin/formNewArtist.html";
//	    }
//
//	    @PostMapping("/admin/artist")
//	    public String newArtist(Model model,@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, @RequestParam("file") MultipartFile image) throws  IOException{
//	        this.artistValidator.validate(artist, bindingResult);
//	        if(!bindingResult.hasErrors()){
//	            Image pic = new Image(image.getBytes());
//	            this.imageRepository.save(pic);
//	            artist.setProfilePicture(pic);
//	            this.artistRepository.save(artist);
//
//	            model.addAttribute("artist", artist);
//	            model.addAttribute("profilePic", pic );
//	            model.addAttribute("userDetails", this.userService.getUserDetails());
//	            return "artist.html";
//	        }
//	        else {
//	            return "/admin/formNewArtist.html";
//	        }
//	    }
//
//	    @GetMapping("/admin/manageDrawings")
//	    public String manageMDrawings(Model model){
//	        model.addAttribute("drawing", this.drawingRepository.findAll());
//	        return "/admin/manageDrawings.html";
//	    }
//
//
//	    @Transactional
//	    @GetMapping("/admin/formUpdateDrawing/{id}")
//	    public String formUpdateDrawing(@PathVariable("id") Long id, Model model){
//	        model.addAttribute("drawing", this.drawingRepository.findById(id).get());
//	        return "/admin/formUpdateDrawing.html";
//	    }
//
//	    @GetMapping("/admin/addArtist/{id}")
//	    public String addDirector(@PathVariable("id") Long id, Model model){
//	        model.addAttribute("artists", this.artistRepository.findAll());
//	        model.addAttribute("drawing", this.drawingRepository.findById(id).get());
//	        return "/admin/addArtist.html";
//	    }
//
//	    @Transactional
//	    @GetMapping("/admin/setArtistToDrawing/{artistId}/{movieId}")
//	    public String setArtistToDrawing(@PathVariable("artistId") Long artistId, @PathVariable("drawingId") Long drawingId, Model model){
//	        Artist director = this.artistRepository.findById(artistId).get();
//	        Drawing drawing = this.drawingRepository.findById(drawingId).get();
//	        drawing.setArtist(director);
//	        this.artistRepository.save(director);
//	        this.drawingRepository.save(drawing);
//
//	        model.addAttribute("drawing", drawing);
//	        return "/admin/formUpdateDrawing.html";
//	    }
//
//	    @Transactional
//	    @GetMapping("/admin/removeArtistToDrawing/{drawingId}")
//	    public String removeArtistToDrawing(@PathVariable("drawingId") Long drawingId, Model model){
//	    	Drawing drawing= this.drawingRepository.findById(drawingId).get();
//	        Artist artist = drawing.getArtist();
//	        artist.getDrawingsArtist().remove(drawing);
//	        this.artistRepository.save(artist);
//	        drawing.setArtist(null);
//	        this.drawingRepository.save(drawing);
//	        model.addAttribute("drawing", drawing);
//	        return "/admin/formUpdateDrawing.html";
//	    }
//
//	   /* @Transactional
//	    @GetMapping("/admin/updateActorsOnMovie/{id}")
//	    public String updateActors(@PathVariable("id") Long id,Model model ){
//
//	        Set<Artist> actorsToAdd = this.actorsToAdd(id);
//	        model.addAttribute("movie", this.movieRepository.findById(id).get());
//	        model.addAttribute("actorsToAdd", actorsToAdd);
//
//	        return "/admin/actorsToAdd.html";
//	    }*/
//	    
}
