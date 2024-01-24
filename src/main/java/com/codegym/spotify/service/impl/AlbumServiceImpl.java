package com.codegym.spotify.service.impl;

import com.codegym.spotify.dto.AlbumDto;
import com.codegym.spotify.entity.Album;
import com.codegym.spotify.repository.AlbumRepository;
import com.codegym.spotify.repository.ArtistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements com.codegym.spotify.service.AlbumService {
    private final ModelMapper modelMapper;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(ModelMapper modelMapper, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.modelMapper = modelMapper;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    private Album convertToAlbumEntity(AlbumDto albumDto) {
        Album  album = modelMapper.map(albumDto, Album.class);
        if(albumDto.getArtistId() != null) {
            artistRepository.findById(albumDto.getArtistId())
                    .ifPresent(album::setArtist);
        }
        return album;
    }

    private AlbumDto convertToAlbumDto(Album album) {
        AlbumDto albumDto = modelMapper.map(album, AlbumDto.class);
        if(album.getArtist() != null) {
            albumDto.setArtistId(album.getArtist().getId());
        }
        return albumDto;
    }

    @Override
    public void saveAlbum(AlbumDto albumDto) {
        Album album = convertToAlbumEntity(albumDto);
        albumRepository.save(album);
    }

    @Override
    public AlbumDto findAlbumById(Long albumId) {
        Album album = albumRepository.findAlbumById(albumId);
        return convertToAlbumDto(album);
    }
    @Override
    public List<AlbumDto> findAlbumByArtistId(Long artistId) {
        List<Album> albums = albumRepository.findAlbumsByArtistId(artistId);
        return albums.stream().map(this::convertToAlbumDto).collect(Collectors.toList());
    }

    @Override
    public List<AlbumDto> findAllAlbum() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream().map(this::convertToAlbumDto).collect(Collectors.toList());
    }
}
