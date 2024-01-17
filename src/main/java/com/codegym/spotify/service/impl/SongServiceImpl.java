package com.codegym.spotify.service.impl;

import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.entity.Song;
import com.codegym.spotify.repository.SongRepository;
import com.codegym.spotify.service.SongService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {
    private SongRepository songRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public SongServiceImpl(SongRepository songRepository, ModelMapper modelMapper) {
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<SongDto> findAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private SongDto convertToDto(Song song) {
        return modelMapper.map(song, SongDto.class);
    }

    private Song convertToEntity(SongDto songDto) {
        return modelMapper.map(songDto, Song.class);
    }

}
