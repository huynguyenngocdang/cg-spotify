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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/artist")
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

    @GetMapping("")
    public String displayArtistList(Model model) {
        List<ArtistDto> artists = artistService.findAllArtist();
        model.addAttribute("artists", artists);
//        List<String> imageArtistBase64 = artists.stream()
//                .map(artist -> Base64.getEncoder().encodeToString(artist.getArtistImage()))
//                .collect(Collectors.toList());
//        model.addAttribute("imagesBase64", imageArtistBase64);
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "artist/artist-list";
    }

    @GetMapping("/own-artist")
    public String displayOwnArtistList(Model model) {
        List<ArtistDto> artists = artistService.findArtistByUserId();
        model.addAttribute("artists", artists);
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "artist/artist-list";
    }

    @GetMapping("/{artistId}")
    public String displayArtist(@PathVariable("artistId") Long artistId, Model model) {
        ArtistDto artistDto = artistService.findArtistById(artistId);
        List<AlbumDto> albums = albumService.findAlbumsByArtistId(artistId);
        List<SongDto> songs = songService.findSongsByArtistId(artistId);
        model.addAttribute("songs", songs);
        model.addAttribute("albums", albums);
        model.addAttribute("artist", artistDto);

        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "artist/artist-details";
    }

    @GetMapping("/{artistId}/albums/new")
    public String displayNewAlbumForm(@PathVariable("artistId") Long artistId, Model model) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setArtistId(artistId);
        model.addAttribute("newAlbum", albumDto);
        model.addAttribute("artistId", artistId);
        return "album/album-new";
    }

    @PostMapping("/{artistId}/albums/new")
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

    @GetMapping("/image/{artistId}")
    public ResponseEntity<byte[]> getArtistImage(@PathVariable Long artistId) {
        ArtistDto artistDto = artistService.findArtistById(artistId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(artistDto.getArtistImage());
    }

    @GetMapping("/new")
    public String displayNewArtistForm(Model model) {
        model.addAttribute("artist", new ArtistDto());
        return "artist/artist-new";
    }

    @PostMapping("/new")
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

    @GetMapping("/edit/{artistId}")
    public String editArtist(@PathVariable("artistId") Long artistId,
                             Model model) {
        ArtistDto artistDto = artistService.findArtistById(artistId);
        model.addAttribute("artist", artistDto);
        model.addAttribute("artistId", artistId);

        UserEntity user = userService.getCurrentUser();
        if(user.getId().equals(artistDto.getCreatedById())) {
            if (artistDto.getArtistImage() != null) {
                String imageArtistBase64 = Base64.getEncoder().encodeToString(artistDto.getArtistImage());
                model.addAttribute("imageBase64", imageArtistBase64);
            }
            return "artist/artist-edit";
        } else {
            return "redirect:/forbidden";
        }

    }

    @PostMapping("/edit/{artistId}")
    public String updateArtist(@ModelAttribute("artist") ArtistDto artist,
                               @PathVariable("artistId") Long artistId,
                               @RequestParam("imageArtistUpload") MultipartFile file,
                               RedirectAttributes redirectAttributes) {
        try {
            if (file != null && !file.isEmpty()) {
                artist.setArtistImage(file.getBytes());
            }
            ArtistDto artistDto = artistService.findArtistById(artistId);

            UserEntity user = userService.getCurrentUser();
            if(user.getId().equals(artistDto.getCreatedById())) {
                artistService.editArtist(artist, artistId);
                redirectAttributes.addFlashAttribute("success", "Artist updated successfully");
            } else {
                return "redirect:/forbidden";
            }
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "There was an error processing the image");
        }
        return "redirect:/artist";
    }

    @PostMapping("/delete/{artistId}")
    public String deleteArtist(@PathVariable("artistId")Long artistId) {
        ArtistDto artistDto = artistService.findArtistById(artistId);
        UserEntity user = userService.getCurrentUser();
        if(artistDto.getCreatedById().equals(user.getId())) {
            artistService.deleteArtist(artistId);
            return "redirect:/artist";
        } else {
            return "redirect:/forbidden";
        }
    }
}
