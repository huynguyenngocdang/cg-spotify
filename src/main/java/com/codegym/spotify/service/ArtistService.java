package com.codegym.spotify.service;

import com.codegym.spotify.dto.ArtistDto;

import java.util.List;

public interface ArtistService {
    List<ArtistDto> findAllArtist();
    List<ArtistDto> findArtistsByName(String keyword);

    List<ArtistDto> findArtistByUserId();
    void saveArtist(ArtistDto artistDto);
    ArtistDto findArtistById(Long id);
//    ArtistDto findArtistBySongId(Long songId);
    void editArtist(ArtistDto artistDto, Long artistId);

    void deleteArtist(Long artistId);
}
