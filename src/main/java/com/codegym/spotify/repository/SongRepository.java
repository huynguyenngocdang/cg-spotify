package com.codegym.spotify.repository;

import com.codegym.spotify.entity.Artist;
import com.codegym.spotify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findSongsByAlbumId(Long albumId);
    Song findSongById(Long songId);
    List<Song> findSongsByTitleLikeIgnoreCase(String keyword);

    List<Song> findSongByTitleContainsIgnoreCase(String keyword);
}
