package com.codegym.spotify.repository;

import com.codegym.spotify.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findArtistById(Long id);
}
