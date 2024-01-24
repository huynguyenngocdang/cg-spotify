package com.codegym.spotify.controller;

import com.codegym.spotify.dto.ArtistDto;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.service.ArtistService;
import com.codegym.spotify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ArtistController(ArtistService artistService, UserService userService) {
        this.artistService = artistService;
        this.userService = userService;
    }

    @GetMapping("/artist")
    public String displayArtistList(Model model) {
        List<ArtistDto> artists = artistService.findAllArtist();
        model.addAttribute("artists", artists);
        List<String> imageArtistBase64= artists.stream()
                .map(artist -> Base64.getEncoder().encodeToString(artist.getArtistImage()))
                .collect(Collectors.toList());
        model.addAttribute("imagesBase64", imageArtistBase64);

        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "artist/artist-list";
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
                             @RequestParam("imageArtistUpload")MultipartFile imageFile,
                             BindingResult result,
                             Model model)  {
        if(result.hasErrors()){
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
}
