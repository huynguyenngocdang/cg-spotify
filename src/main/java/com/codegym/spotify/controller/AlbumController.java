package com.codegym.spotify.controller;

import com.codegym.spotify.dto.AlbumDto;
import com.codegym.spotify.service.AlbumService;
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
import java.util.List;

@Controller
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

//    @GetMapping("/{artistId}/albums")
//    public String displayAlbumList(@PathVariable("artistId")Long artistId,
//            Model model) {
//        List<AlbumDto> albumList = albumService.findAlbumByArtistId(artistId);
//        model.addAttribute("albumList", albumList);
//        model.addAttribute("artistId", artistId);
//        return "album/album-list";
//    }

    @GetMapping("/{artistId}/albums/new")
    public String displayNewAlbumForm(@PathVariable("artistId") Long artistId, Model model) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setArtistId(artistId);
        model.addAttribute("newAlbum", albumDto);
        model.addAttribute("artistId", artistId);
        return "album/album-new";
    }

    @GetMapping("/albums/image/{albumId}")
    public ResponseEntity<byte[]> getAlbumImage(@PathVariable Long albumId) {
        AlbumDto albumDto = albumService.findAlbumById(albumId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(albumDto.getAlbumImage());
    }

    @PostMapping("/{artistId}/albums/new")
    public String saveNewAlbum(@PathVariable("artistId") Long artistId,
                               @Valid @ModelAttribute("newAlbum") AlbumDto albumDto,
                               @RequestParam("imageAlbumUpload") MultipartFile file,
                               BindingResult result,
                               Model model) {
        albumDto.setArtistId(artistId);
        if(result.hasErrors()) {
            model.addAttribute("newAlbum", albumDto);
            return "album/album-new";
        }
        try{
            albumDto.setAlbumImage(file.getBytes());
            albumService.saveAlbum(albumDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("artistId", artistId);
//        return String.format("redirect:/%d/albums", artistId);
        return "artist/artist-details";
    }

}
