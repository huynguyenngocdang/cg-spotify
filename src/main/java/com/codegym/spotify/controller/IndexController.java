package com.codegym.spotify.controller;

import com.codegym.spotify.dto.AlbumDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.service.AlbumService;
import com.codegym.spotify.service.SongService;
import com.codegym.spotify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    private SongService songService;
    private UserService userService;
    private AlbumService albumService;

    @Autowired
    public IndexController(SongService songService, UserService userService, AlbumService albumService) {
        this.songService = songService;
        this.userService = userService;
        this.albumService = albumService;
    }

    @GetMapping({"/", "/index"})
    public String displayIndex(Model model) {
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        List<AlbumDto> albumDtos = albumService.findAllAlbum();
        model.addAttribute("albums", albumDtos);
        return "index/index";
    }

    @GetMapping("account")
    public String account(Model model) {
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "account/account-menu";
    }


}
