package com.codegym.spotify.repository;

import com.codegym.spotify.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findByTitle(String url);

}
