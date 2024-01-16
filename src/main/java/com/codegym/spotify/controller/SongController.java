package com.codegym.spotify.controller;

import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.model.Playlist;
import com.codegym.spotify.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/", "/songs"})
public class SongController {
    private SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("")
    public ModelAndView listSongs() {
        List<SongDto> songs = songService.findAllSongs();
        ModelAndView modelAndView = new ModelAndView("index/index");
        modelAndView.addObject("songs", songs);

        Playlist playlist1 = new Playlist();
        playlist1.setTitle("Taylor");
        playlist1.setPhotoUrl("https://upload.wikimedia.org/wikipedia/en/1/1f/Taylor_Swift_-_Taylor_Swift.png");
        playlist1.setDescription("https://upload.wikimedia.org/wikipedia/en/1/1f/Taylor_Swift_-_Taylor_Swift.png");
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        playlists.add(playlist1);
        modelAndView.addObject("playlists", playlists);
        return modelAndView;
    }

    @GetMapping("/login")
    public String login() {
        return "/login/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/register/register";
    }

    @GetMapping("/search")
    public String searchIndex() {
        return "/index/index-search";
    }
}
