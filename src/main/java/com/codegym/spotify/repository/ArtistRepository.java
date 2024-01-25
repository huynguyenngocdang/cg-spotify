package com.codegym.spotify.repository;

import com.codegym.spotify.entity.Album;
import com.codegym.spotify.entity.Artist;
import com.codegym.spotify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findArtistById(Long id);
    List<Artist> findArtistsByNameContainsIgnoreCase(String keyword);
}
