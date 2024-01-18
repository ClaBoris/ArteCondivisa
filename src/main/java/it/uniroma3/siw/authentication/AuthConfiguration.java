package it.uniroma3.siw.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.uniroma3.siw.controller.validator.CustomAccessDeniedHandler;
import it.uniroma3.siw.model.Credentials;
//import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;
//import static it.uniroma3.siw.model.Credentials.DEFAULT_ROLE;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity)throws Exception{
		httpSecurity
		.csrf().and().cors().disable()
		.authorizeHttpRequests()
		//.requestMatchers(/**).permitAll()
		//chiunque autenticato o no può accedere alle pagine index, login, register, css e immagini
		.requestMatchers(HttpMethod.GET,"/", "/index", "/register", "/login", "/css/**", "/images/**", "favicon.ico", "/formNewDrawing", "/formNewArtist", "/newArtist", "/artist/{artistId}/drawings", "/formUpdateDrawing/{id}", "/newDrawing/{id}", "/newDrawing").permitAll()
		//chiunque autenticato o no può mandaqre3 richieste POST per login e register
		.requestMatchers(HttpMethod.POST, "/register", "/login", "/formNewDrawing").permitAll()
		// solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
		.requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
		.requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
		.requestMatchers(HttpMethod.GET, "/admin/removeDrawing/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
		.requestMatchers(HttpMethod.POST, "/admin/removeDrawing/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
		.requestMatchers(HttpMethod.GET, "/admin/removeArtistAndDrawings/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
		.requestMatchers(HttpMethod.POST, "/admin/removeArtistAndDrawings/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
		.requestMatchers("/error/**").permitAll()
		
		//richieste di altri utenti autenticati
		.anyRequest().authenticated()
		//FORM LOGIN
		.and().formLogin()
		.loginPage("/login")
		.permitAll()
		.defaultSuccessUrl("/success")
		.failureUrl("/login?error=true")
		//LOGOUT
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.clearAuthentication(true)
		.permitAll()
		.and()
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		return httpSecurity.build();
	}
	
	@Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler(); // Implementa una classe CustomAccessDeniedHandler per gestire l'accesso negato
    }
}
