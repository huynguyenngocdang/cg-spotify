package com.codegym.spotify.service;

import com.codegym.spotify.dto.AlbumDto;

import java.util.List;

public interface AlbumService {
    void saveAlbum(AlbumDto albumDto);
    AlbumDto findAlbumById(Long albumId);

    List<AlbumDto> findAlbumsByArtistId(Long artistId);

}
