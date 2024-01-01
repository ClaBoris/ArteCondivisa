package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Drawing;

public interface DrawingRepository extends CrudRepository<Drawing,Long>{
	boolean existsByName(String name);

	@Query(value = "SELECT * FROM drawing order by id desc limit :limit", nativeQuery = true)
	public List<Drawing> findTopN(@Param("limit") int limit);

}
