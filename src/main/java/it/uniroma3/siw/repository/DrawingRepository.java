package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Drawing;

@Repository
public interface DrawingRepository extends CrudRepository<Drawing,Long>{
	boolean existsByName(String name);

	@Query(value = "SELECT * FROM drawing order by id desc limit :limit", nativeQuery = true)
	public List<Drawing> findTopN(@Param("limit") int limit);
	List<Drawing> findByArtistId(Long artistId);
	@Modifying
    @Query("DELETE FROM Drawing d WHERE d.artist.id = :artistId")
	void deleteByArtistId(Long artistId);
	
	
	

}
