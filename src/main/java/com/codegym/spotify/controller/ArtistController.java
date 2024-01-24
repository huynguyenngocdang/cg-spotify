package com.codegym.spotify.controller;

import com.codegym.spotify.dto.AlbumDto;
import com.codegym.spotify.dto.ArtistDto;
import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.entity.Album;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.service.AlbumService;
import com.codegym.spotify.service.ArtistService;
import com.codegym.spotify.service.SongService;
import com.codegym.spotify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArtistController {
    private ArtistService artistService;
    private UserService userService;
    private AlbumService albumService;
    private SongService songService;

    public ArtistController(ArtistService artistService, UserService userService, AlbumService albumService, SongService songService) {
        this.artistService = artistService;
        this.userService = userService;
        this.albumService = albumService;
        this.songService = songService;
    }

    @GetMapping("/artist")
    public String displayArtistList(Model model) {
        List<ArtistDto> artists = artistService.findArtistByUserId();
        model.addAttribute("artists", artists);
        List<String> imageArtistBase64 = artists.stream()
                .map(artist -> Base64.getEncoder().encodeToString(artist.getArtistImage()))
                .collect(Collectors.toList());
        model.addAttribute("imagesBase64", imageArtistBase64);

        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "artist/artist-list";
    }

    @GetMapping("/artist/{artistId}")
    public String displayArtist(@PathVariable("artistId") Long artistId, Model model) {
        ArtistDto artistDto = artistService.findArtistById(artistId);
        List<AlbumDto> albums = albumService.findAlbumByArtistId(artistId);
        List<SongDto> songs = songService.findSongsByArtistId(artistId);
        model.addAttribute("songs", songs);
        model.addAttribute("albums", albums);
        model.addAttribute("artist", artistDto);

        return "artist/artist-details";
    }

    @GetMapping("/artist/image/{artistId}")
    public ResponseEntity<byte[]> getArtistImage(@PathVariable Long artistId) {
        ArtistDto artistDto = artistService.findArtistById(artistId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(artistDto.getArtistImage());
    }

    @GetMapping("/artist/new")
    public String displayNewArtistForm(Model model) {
        model.addAttribute("artist", new ArtistDto());
        return "artist/artist-new";
    }

    @PostMapping("/artist/new")
    public String saveArtist(@Valid @ModelAttribute("artist") ArtistDto artistDto,
                             @RequestParam("imageArtistUpload") MultipartFile imageFile,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("artist", artistDto);
            return "artist/artist-new";
        }
        try {
            artistDto.setArtistImage(imageFile.getBytes());
            artistService.saveArtist(artistDto);
            return "redirect:/artist?successArtist";
        } catch (IOException e) {
            return "redirect:/artist?failArtist";
        }
    }

    @GetMapping("/artist/edit{artistId}")
    public String editArtist(@PathVariable("artistId") Long artistId,
                             Model model) {
        ArtistDto artistDto = artistService.findArtistById(artistId);
        model.addAttribute("artist", artistDto);
        model.addAttribute("artistId", artistId);
        return "artist/artist-edit";
    }
}
