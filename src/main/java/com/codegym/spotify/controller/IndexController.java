package com.codegym.spotify.controller;

import com.codegym.spotify.dto.AlbumDto;
import com.codegym.spotify.dto.ArtistDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.service.AlbumService;
import com.codegym.spotify.service.ArtistService;
import com.codegym.spotify.service.SongService;
import com.codegym.spotify.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    private final SongService songService;
    private final UserService userService;
    private final AlbumService albumService;
    private final ArtistService artistService;


    public IndexController(SongService songService, UserService userService, AlbumService albumService, ArtistService artistService) {
        this.songService = songService;
        this.userService = userService;
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping({"/", "/index"})
    public String displayIndex(Model model) {
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        List<AlbumDto> albumDtos = albumService.findAllAlbum();
        List<ArtistDto> artistDtos = artistService.findAllArtist();
        model.addAttribute("albums", albumDtos);
        model.addAttribute("artists", artistDtos);
        return "index/index";
    }

    @GetMapping("account")
    public String account(Model model) {
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "account/account-menu";
    }
}
