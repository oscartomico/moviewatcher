package com.moviewatcher.repositories;

import java.util.List;

import com.moviewatcher.entities.Movie;

import org.springframework.data.repository.CrudRepository;

public interface MoviesRepository extends CrudRepository<Movie, Long>{
	
	List<Movie> findAllByTitle(String title);

	/*@Modifying
	@Transactional
	@Query("UPDATE Movie SET watched = ?1 WHERE id = ?2")
	void updateWatched(boolean watched, Long id);*/
	
}
