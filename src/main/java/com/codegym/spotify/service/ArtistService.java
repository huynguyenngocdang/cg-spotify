package com.codegym.spotify.service;

import com.codegym.spotify.dto.ArtistDto;

import java.util.List;

public interface ArtistService {
    List<ArtistDto> findAllArtist();
    List<ArtistDto> findArtistByUserId();
    void saveArtist(ArtistDto artistDto);
    ArtistDto findArtistById(Long id);

    void editArtist(ArtistDto artistDto, Long artistId);

    void deleteArtist(Long artistId);
}
