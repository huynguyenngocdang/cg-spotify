package com.codegym.spotify.controller;

import com.codegym.spotify.dto.AlbumDto;
import com.codegym.spotify.dto.ArtistDto;
import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.service.AlbumService;
import com.codegym.spotify.service.ArtistService;
import com.codegym.spotify.service.SongService;
import com.codegym.spotify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final UserService userService;

    @Autowired
    public AlbumController(AlbumService albumService, ArtistService artistService, SongService songService, UserService userService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping("/new/{artistId}")
    public String displayNewAlbumForm(@PathVariable("artistId") Long artistId, Model model) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setArtistId(artistId);
        model.addAttribute("newAlbum", albumDto);
        model.addAttribute("artistId", artistId);
        return "album/album-new";
    }

    @PostMapping("/new/{artistId}")
    public String saveNewAlbum(@PathVariable("artistId") Long artistId,
                               @Valid @ModelAttribute("newAlbum") AlbumDto albumDto,
                               @RequestParam("imageAlbumUpload") MultipartFile file,
                               BindingResult result,
                               Model model) {
        albumDto.setArtistId(artistId);
        if (result.hasErrors()) {
            model.addAttribute("newAlbum", albumDto);
            return "album/album-new";
        }
        try {
            albumDto.setAlbumImage(file.getBytes());
            albumService.saveAlbum(albumDto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArtistDto artistDto = artistService.findArtistById(artistId);
        model.addAttribute("artist", artistDto);
        model.addAttribute("artistId", artistId);
//        return String.format("redirect:/%d/albums", artistId);
        return "redirect:/artist/" + artistId;
    }

    @GetMapping("/image/{albumId}")
    public ResponseEntity<byte[]> getAlbumImage(@PathVariable Long albumId) {
        AlbumDto albumDto = albumService.findAlbumById(albumId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(albumDto.getAlbumImage());
    }

    @GetMapping("/detail/{albumId}/songs")
    public String listSongs(@PathVariable("albumId") Long albumId, Model model) {
        List<SongDto> songs = songService.findSongsByAlbumId(albumId);
        model.addAttribute("songs", songs);
        model.addAttribute("albumId", albumId);
        AlbumDto albumDto = albumService.findAlbumById(albumId);
        ArtistDto artistDto = artistService.findArtistById(albumDto.getArtistId());
        model.addAttribute("artist", artistDto);
        model.addAttribute("album", albumDto);
        return "song/songs-list";
    }

    @GetMapping("/edit/{albumId}")
    public String displayEditAlbum(@PathVariable("albumId") Long albumId,
                                   Model model) {
        AlbumDto albumDto = albumService.findAlbumById(albumId);
        model.addAttribute("album", albumDto);
        
        UserEntity user= userService.getCurrentUser();
        ArtistDto artistDto = artistService.findArtistById(albumDto.getArtistId());
        if (user.getId().equals(artistDto.getCreatedById())) {
            if (albumDto.getAlbumImage() != null) {
                String imageArtistBase64 = Base64.getEncoder().encodeToString(albumDto.getAlbumImage());
                model.addAttribute("imageBase64", imageArtistBase64);
            }
            return "album/album-edit";
        } else {
            return "redirect:/forbidden";
        }
    }

    @PostMapping("/edit/{albumId}")
    public String editAlbum(@PathVariable("albumId") Long albumId,
                            @Valid @ModelAttribute("album") AlbumDto albumDto,
                            @RequestParam("imageAlbumUpload") MultipartFile file,
                            BindingResult result,
                            Model model) {
        try {
            if (file != null && !file.isEmpty()) {
                albumDto.setAlbumImage(file.getBytes());
            } else {
                AlbumDto oldAlbum = albumService.findAlbumById(albumId);
                albumDto.setAlbumImage(oldAlbum.getAlbumImage());
            }
            UserEntity user = userService.getCurrentUser();
            ArtistDto artistDto = artistService.findArtistById(albumDto.getArtistId());
            if (user.getId().equals(artistDto.getCreatedById())) {
                albumService.editAlbum(albumDto, albumId);
                return "redirect:/albums/detail/" +
                        albumId +
                        "/songs";
            } else {
                return "redirect:/forbidden";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/albums/detail/" +
                albumId +
                "/songs";

    }

    @PostMapping("/delete/{albumId}")
    public String deleteAlbum(@PathVariable("albumId") Long albumId,
                              Model model) {
        UserEntity user = userService.getCurrentUser();
        AlbumDto albumDto = albumService.findAlbumById(albumId);
        ArtistDto artistDto = artistService.findArtistById(albumDto.getArtistId());
        if(user.getId().equals(artistDto.getCreatedById())) {
            albumService.deleteAlbum(albumId);

            return "redirect:/artist/"+artistDto.getId();
        } else {
            return "redirect:/forbidden";
        }
    }
}
