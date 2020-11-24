package com.moviewatcher.controllers;

import com.moviewatcher.entities.TVShow;
import com.moviewatcher.services.TVShowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class TVShowController {
    @Autowired
    private TVShowService tvShowService;

    @RequestMapping("tvshow/list")
    public String list(Model model) {
        return "tvshow/list";
    }

    @RequestMapping("tvshow/add")
    public String addTVShow(Model model, @ModelAttribute TVShow tvShow) {
		if ((tvShow.getTitle() != null && !tvShow.getTitle().isEmpty())
				|| (tvShow.getImdbID() != null && !tvShow.getImdbID().isEmpty())) {
			TVShow tvShowResponse = tvShowService.findInRest(tvShow);

			model.addAttribute("tvShowFinded", tvShowResponse);
		}
        return "tvshow/add";
    }

    @RequestMapping(value = "tvshow/save", method = RequestMethod.POST)
    public String save(@ModelAttribute TVShow tvShow) {
        TVShow tvShowResponse = tvShowService.findAllInRest(tvShow);
        tvShowService.addTVShow(tvShowResponse);

        return "redirect:tvshow/list";
    }
}