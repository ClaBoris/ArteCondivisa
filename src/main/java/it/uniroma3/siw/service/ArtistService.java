package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.DrawingRepository;
import jakarta.transaction.Transactional;

@Service
public class ArtistService {
	@Autowired
	ArtistRepository artistRepository;
	@Autowired
	DrawingRepository drawingRepository;

	@Transactional
	public void removeArtistAndDrawings(Long artistId) {
		// Ottieni l'artista
		Artist artist = artistRepository.findById(artistId).orElse(null);

		if (artist != null) {
			// Elimina i disegni associati all'artista
			drawingRepository.deleteByArtistId(artistId);

			// Elimina l'artista
			artistRepository.delete(artist);
		}
	}

	@Transactional
	public void save(Artist artist){
		artistRepository.save(artist);
	}

}
