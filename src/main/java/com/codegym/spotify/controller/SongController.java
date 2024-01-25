package com.codegym.spotify.controller;

import com.codegym.spotify.dto.AlbumDto;
import com.codegym.spotify.dto.ArtistDto;
import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.service.AlbumService;
import com.codegym.spotify.service.ArtistService;
import com.codegym.spotify.service.LyricService;
import com.codegym.spotify.service.SongService;
import com.codegym.spotify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SongController {
    private final SongService songService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final LyricService lyricService;
    private final UserService userService;


    @Autowired
    public SongController(SongService songService,
                          AlbumService albumService,
                          ArtistService artistService,
                          LyricService lyricService,
                          UserService userService
                          ) {
        this.songService = songService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.lyricService = lyricService;
        this.userService = userService;
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


    @GetMapping("/{albumId}/songs/upload")
    public String displayUploadSong(@PathVariable("albumId") Long albumId, Model model) {
        AlbumDto albumDto = albumService.findAlbumById(albumId);
        ArtistDto artistDto = artistService.findArtistById(albumDto.getArtistId());
        UserEntity user = userService.getCurrentUser();
        if(user.getId().equals(artistDto.getCreatedById())) {
            SongDto song = new SongDto();
            song.setAlbumId(albumId);
            model.addAttribute("song", song);
            model.addAttribute("albumId", albumId);
            return "song/upload";
        } else {
            return "redirect:/forbidden";
        }
    }

    @PostMapping("/{albumId}/songs/upload")
    public String uploadSong(@Valid @ModelAttribute("song") SongDto songDto,
                             @PathVariable("albumId") Long albumId,
                             BindingResult result,
                             @RequestParam("file") MultipartFile file,
                             Model model) {
        if(result.hasErrors()){
            model.addAttribute("song", songDto);
            return "song/upload";
        }

        String songFilename = songDto.getFilename().replace(" ", "").toLowerCase();
        songDto.setFilename(songFilename);

        if(songService.handleSongUpload(file, songDto.getFilename()) ){
            songService.saveSong(songDto, file);
            List<SongDto> songs = songService.findSongsByAlbumId(albumId);
            model.addAttribute("songs", songs);
            model.addAttribute("albumId", albumId);
            AlbumDto albumDto = albumService.findAlbumById(albumId);
            ArtistDto artistDto = artistService.findArtistById(albumDto.getArtistId());
            model.addAttribute("artist", artistDto);
            model.addAttribute("album", albumDto);
            return ("redirect:/albums/detail/" + albumId + "/songs");
        }
        return "redirect:/index?uploadFail";
    }

    @GetMapping("/{artistId}/{songId}/songs/songsDetail")
    public String displaySongDetails(@PathVariable("artistId") Long artistId,
                                     @PathVariable("songId") Long songId,
                                     Model model) {
        SongDto songDto = songService.findSongById(songId);
        String songName = songDto.getTitle();
        ArtistDto artistDto = artistService.findArtistById(artistId);
        String artistName = artistDto.getName();

        List<String> lyrics = lyricService.getSongLyrics(artistName, songName);
        model.addAttribute("lyrics", lyrics);
        model.addAttribute("song", songDto);
        model.addAttribute("artist", artistDto);
        AlbumDto albumDto = albumService.findAlbumById(songDto.getAlbumId());
        model.addAttribute("album", albumDto);
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "song/songs-detail-giang";
    }

    @PostMapping("/song/delete/{songId}")
    public String deleteSong(@PathVariable("songId")Long songId) {
        SongDto songDto = songService.findSongById(songId);
        AlbumDto albumDto = albumService.findAlbumById(songDto.getAlbumId());
        ArtistDto artistDto = artistService.findArtistById(albumDto.getArtistId());

        UserEntity user = userService.getCurrentUser();
        if(user.getId().equals(artistDto.getCreatedById())) {
            if(songService.deleteSong(songId)){
                return "redirect:/artist";
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/forbidden";
        }
    }
}
