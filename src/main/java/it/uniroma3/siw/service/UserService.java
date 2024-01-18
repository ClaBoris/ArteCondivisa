package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	protected CredentialsService credentialsService;
	@Autowired
	protected UserRepository userRepository;

	@Transactional
	public User getUser(Long id) {
		Optional<User> result = this.userRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	@Transactional
	public UserDetails getUserDetails(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = null;
		if(!(authentication instanceof AnonymousAuthenticationToken)){
			userDetails = (UserDetails)authentication.getPrincipal();
		}
		return userDetails;
	}

	@Transactional
	public User getCurrentUser() {
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			String username = authentication.getName();
			user = credentialsService.getCredentials(username).getUser();
		}
		return user;
	}
	@Transactional
	public User getAdminUser() {
	    User adminUser = null;
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
	        String adminUsername = authentication.getName();
	        Credentials adminCredentials = credentialsService.getCredentials(adminUsername);
	        if (adminCredentials != null && Credentials.ADMIN_ROLE.equals(adminCredentials.getRole())) {
	            adminUser = adminCredentials.getUser();
	        }
	    }
	    return adminUser;
	}

}
