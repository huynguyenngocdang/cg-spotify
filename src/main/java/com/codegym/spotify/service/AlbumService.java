package com.codegym.spotify.service;

import com.codegym.spotify.dto.AlbumDto;
import com.codegym.spotify.dto.SongDto;

import java.util.List;

public interface AlbumService {
    void saveAlbum(AlbumDto albumDto);
    AlbumDto findAlbumById(Long albumId);
    List<AlbumDto> findAlbumsByName(String keyword);

    List<AlbumDto> findAlbumsByArtistId(Long artistId);
    List<AlbumDto> findAllAlbum();
    void editAlbum(AlbumDto albumDto, Long albumId);

    void deleteAlbum(Long albumId);
}
