package com.codegym.spotify.service.impl;

import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.model.Song;
import com.codegym.spotify.repository.SongRepository;
import com.codegym.spotify.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {
    private SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<SongDto> findAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map((song -> mapToSongDto(song))).collect(Collectors.toList());
    }

    private SongDto mapToSongDto(Song song) {
        SongDto songDto = SongDto.builder()
                .id(song.getId())
                .title(song.getTitle())
                .photoUrl(song.getPhotoUrl())
                .artistId(song.getArtistId())
                .albumId(song.getAlbumId())
                .createdOn(song.getCreatedOn())
                .updatedOn(song.getUpdatedOn())
                .build();
        return songDto;
    }

}
