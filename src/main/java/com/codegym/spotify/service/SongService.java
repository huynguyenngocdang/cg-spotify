package com.codegym.spotify.service;

import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.entity.Song;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SongService {
    List<SongDto> findAllSongs();
    List<SongDto> findSongsByAlbumId(Long albumId);
    List<SongDto> findSongsByArtistId(Long artistId);

    SongDto findSongById(Long songId);

    SongDto convertToSongDto(Song song);
    Song convertToSongEntity(SongDto songDto);
    boolean handleSongUpload(MultipartFile file, String fileName);
    void saveSong(SongDto songDto, MultipartFile file);

}
