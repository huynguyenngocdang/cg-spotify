package com.codegym.spotify.controller;

import com.codegym.spotify.dto.AlbumDto;
import com.codegym.spotify.dto.ArtistDto;
import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.service.AlbumService;
import com.codegym.spotify.service.ArtistService;
import com.codegym.spotify.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SearchController {
    private SongService songService;
    private AlbumService albumService;
    private ArtistService artistService;

    @Autowired
    public SearchController(SongService songService, AlbumService albumService, ArtistService artistService) {
        this.songService = songService;
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping("/search")
    public String searchIndex() {
        return "index/index-search";
    }

    @GetMapping("/search/result")
    public String searchCollections(@RequestParam(name="searchKeyword") String searchKeyword, Model model) {


        List<SongDto> songs = songService.findSongsByName(searchKeyword);
        List<ArtistDto> artists = artistService.findArtistsByName(searchKeyword);
        List<AlbumDto> albums = albumService.findAlbumsByName(searchKeyword);
        model.addAttribute("songs", songs);
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        return "index/index-search-result";
    }

    @GetMapping("/search/song/{songId}")
    public String displaySongSearch(@PathVariable("songId") Long songId) {
        SongDto songDto = songService.findSongById(songId);
        AlbumDto albumDto = albumService.findAlbumById(songDto.getAlbumId());
        ArtistDto artistDto = artistService.findArtistById(albumDto.getArtistId());

        return "redirect:/" +artistDto.getId() +"/"+ songId +"/songs/songsDetail";
    }
}
