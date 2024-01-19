package com.codegym.spotify.controller;

import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SongController {
    private SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs")
    public ModelAndView listSongs() {
        List<SongDto> songs = songService.findAllSongs();
        ModelAndView modelAndView = new ModelAndView("index/index");
        modelAndView.addObject("songs", songs);
        return modelAndView;
    }


    @GetMapping("/search")
    public String searchIndex() {
        return "index/index-search";
    }

}
