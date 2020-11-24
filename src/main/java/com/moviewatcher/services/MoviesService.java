package com.moviewatcher.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moviewatcher.entities.Movie;
import com.moviewatcher.repositories.MoviesRepository;

@Service
public class MoviesService {
	@Value("${api.key}")
	private String apiKey;

	@Autowired
	private MoviesRepository moviesRepository;
	
	public Movie findInRest(Movie movie) {
		RestTemplate restTemplate = new RestTemplate();
		String urlPath
		  = "http://www.omdbapi.com/?" + apiKey;
		if (movie.getTitle() != null && !movie.getTitle().isEmpty()) {
			urlPath += "&t=" + movie.getTitle().replace(' ', '+');
		}
		if (movie.getImdbID() != null && !movie.getImdbID().isEmpty()) {
			urlPath +="&i=" + movie.getImdbID();
		}
		Movie movieResponse
		  = restTemplate.getForObject(urlPath, Movie.class);
		return movieResponse;
	}
	
	public void addMovie(Movie movie) {
		moviesRepository.save(movie);
	}
	
	public List<Movie> listAllMovies() {
		List<Movie> result = new ArrayList<Movie>();
		moviesRepository.findAll().forEach(result::add);
		return result;
	}
	
	public Movie getMovieById(Long id) {
		return moviesRepository.findById(id).get();	
	}

	public void setWatched(Long id) {
		Movie auxMovie = moviesRepository.findById(id).get();
		auxMovie.setWatched(!auxMovie.isWatched());
		addMovie(auxMovie);
	}
}
