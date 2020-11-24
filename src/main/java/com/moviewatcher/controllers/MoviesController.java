package com.moviewatcher.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moviewatcher.entities.Movie;
import com.moviewatcher.services.MoviesService;

@Controller
public class MoviesController {
	@Autowired
	private MoviesService moviesService;

	@RequestMapping("movie/add")
	public String addMovie(Model model, @RequestParam(value = "", required = false) String title,
			@ModelAttribute Movie movie) {
		if ((movie.getTitle() != null && !movie.getTitle().isEmpty())
				|| (movie.getImdbID() != null && !movie.getImdbID().isEmpty())) {
			Movie movieResponse = moviesService.findInRest(movie);

			model.addAttribute("movieFinded", movieResponse);
		}
		return "movie/add";
	}

	@RequestMapping(value = "movie/save", method = RequestMethod.POST)
	public String save(@ModelAttribute Movie movie) {
		moviesService.addMovie(movie);
		return "redirect:/movie/add";
	}

	@RequestMapping("movie/list")
	public String list(Model model) {
		model.addAttribute("moviesList", moviesService.listAllMovies());
		return "movie/list";
	}

	@RequestMapping("movie/details/{id}")
	public String details(Model model, @PathVariable Long id) {
		model.addAttribute("movie", moviesService.getMovieById(id));
		return "movie/details";
	}

	@RequestMapping("movie/watched/{id}")
	public String setWatched(@PathVariable Long id) {
		moviesService.setWatched(id);
		return "redirect:/movie/details/" + id;
	}
}
