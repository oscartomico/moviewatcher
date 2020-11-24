package com.moviewatcher.services;

import java.util.ArrayList;
import java.util.List;

import com.moviewatcher.entities.Episode;
import com.moviewatcher.entities.Season;
import com.moviewatcher.entities.TVShow;
import com.moviewatcher.repositories.TVShowRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TVShowService {
	@Value("${api.key}")
	private String apiKey;
	@Autowired
	private TVShowRepository tvShowRepository;

	/*
	 This method return tv show basic information with cover
	*/
	public TVShow findInRest(TVShow tvShow) {
		RestTemplate restTemplate = new RestTemplate();
		String urlPath
		  = "http://www.omdbapi.com/?" + apiKey;
		if (tvShow.getTitle() != null && !tvShow.getTitle().isEmpty()) {
			urlPath += "&t=" + tvShow.getTitle().replace(' ', '+');
		}
		if (tvShow.getImdbID() != null && !tvShow.getImdbID().isEmpty()) {
			urlPath +="&i=" + tvShow.getImdbID();
		}
		TVShow tvShowResponse
		  = restTemplate.getForObject(urlPath, TVShow.class);

		return tvShowResponse;
	}

	/*
	 This method return tv show full information, no cover
	*/
    public TVShow findAllInRest(TVShow tvShowResponse) {
		for (int i = 1; i <= tvShowResponse.getNumberSeason(); i++) {
			Season season = findSeasonInRest(tvShowResponse.getTitle(), i);
			addSeason(tvShowResponse, season);
		}
		return tvShowResponse;
	}

	private void addSeason(TVShow tvShow, Season season) {
		if (tvShow.getSeasons() == null) {
			tvShow.setSeasons(new ArrayList<Season>());			
		}
		tvShow.getSeasons().add(season);
	}
	
	private Season findSeasonInRest(String showTitle, int seasonId) {
		RestTemplate restTemplate = new RestTemplate();
		String urlPath
		  = "http://www.omdbapi.com/?" + apiKey + "&t=" + showTitle + "&Season=" + seasonId;
		return restTemplate.getForObject(urlPath, Season.class);
	}

    public List<TVShow> listAllTVShows() {
		List<TVShow> list = new ArrayList<TVShow>();
		tvShowRepository.findAll().forEach(list::add);
		return list;
	}
	
	public void addTVShow(TVShow tvShow) {
		tvShowRepository.save(tvShow);
	}
}