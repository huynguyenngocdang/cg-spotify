package com.codegym.spotify.controller;

import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.entity.Playlist;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.security.SecurityUtil;
import com.codegym.spotify.service.SongService;
import com.codegym.spotify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private SongService songService;
    private UserService userService;

    @Autowired
    public IndexController(SongService songService, UserService userService) {
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping({"/", "/index"})
    public String displayIndex(Model model) {
        UserEntity user =new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if(username!= null) {
            user = userService.findByUsername(username);
        }
        model.addAttribute("user", user);

//        List<SongDto> songs = songService.findAllSongs();
//        model.addAttribute("songs", songs);
//        Playlist playlist1 = new Playlist();
//        playlist1.setTitle("Taylor");
//        playlist1.setPhotoUrl("https://upload.wikimedia.org/wikipedia/en/1/1f/Taylor_Swift_-_Taylor_Swift.png");
//        playlist1.setDescription("https://upload.wikimedia.org/wikipedia/en/1/1f/Taylor_Swift_-_Taylor_Swift.png");
//        List<Playlist> playlists = new ArrayList<>();
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        playlists.add(playlist1);
//        model.addAttribute("playlists", playlists);
    return "index/index";
    }

    @GetMapping("account")
    public String account() {
        return "account/account-menu";
    }
}
