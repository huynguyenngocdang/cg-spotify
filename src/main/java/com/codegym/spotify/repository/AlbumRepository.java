package com.codegym.spotify.repository;

import com.codegym.spotify.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findAlbumById(Long albumId);
    List<Album> findAlbumsByArtistId(Long artistId);
    List<Album> findAlbumsByTitleContainsIgnoreCase(String keyword);
}
