package com.moviewatcher.repositories;

import java.util.List;
import com.moviewatcher.entities.TVShow;

import org.springframework.data.repository.CrudRepository;

public interface TVShowRepository extends CrudRepository<TVShow, Long>{
	
	List<TVShow> findAllByTitle(String title);

	/*@Modifying
	@Transactional
	@Query("UPDATE Movie SET watched = ?1 WHERE id = ?2")
	void updateWatched(boolean watched, Long id);*/
	
}