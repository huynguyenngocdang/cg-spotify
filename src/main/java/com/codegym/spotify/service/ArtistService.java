package com.codegym.spotify.service;

import com.codegym.spotify.dto.ArtistDto;

import java.util.List;

public interface ArtistService {
    List<ArtistDto> findAllArtist();

    void saveArtist(ArtistDto artistDto);
    ArtistDto findArtistById(Long id);
}
