package com.codegym.spotify.controller.rest;

import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.entity.Song;
import com.codegym.spotify.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
public class AudioController {
    private SongService songService;
    private SongDto currentSong;

    @Autowired
    public AudioController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/current")
    public SongDto getCurrentSong() {
        return currentSong;
    }

    @GetMapping("/change/{songId}")
    public SongDto changeSong(@RequestParam("songId") Long songId) {
        return songService.findSongById(songId);
    }

    @PostMapping("/update")
    public void updateCurrentSong(@RequestBody SongDto newSong) {
        currentSong = newSong;
    }
}