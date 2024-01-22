package com.codegym.spotify.service.impl;

import com.codegym.spotify.dto.ArtistDto;
import com.codegym.spotify.entity.Album;
import com.codegym.spotify.entity.Artist;
import com.codegym.spotify.repository.AlbumRepository;
import com.codegym.spotify.repository.ArtistRepository;
import com.codegym.spotify.service.ArtistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final ModelMapper modelMapper;
    private final AlbumRepository albumRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, ModelMapper modelMapper, AlbumRepository albumRepository) {
        this.artistRepository = artistRepository;
        this.modelMapper = modelMapper;
        this.albumRepository = albumRepository;
    }

    private Artist convertToArtistEntity(ArtistDto artistDto) {
        Artist artist = modelMapper.map(artistDto, Artist.class);
        if (artistDto.getAlbumsId() != null && !artistDto.getAlbumsId().isEmpty()) {
            List<Album> albums = albumRepository.findAllById(artistDto.getAlbumsId());
            artist.setAlbums(albums);
            albums.forEach(album -> album.setArtist(artist));
        }
        return artist;
    }

    private ArtistDto convertToArtistDto(Artist artist) {
        ArtistDto artistDto = modelMapper.map(artist, ArtistDto.class);
        if (artist.getAlbums() != null && !artist.getAlbums().isEmpty()) {
            List<Long> albumIds = artist.getAlbums().stream()
                    .map(Album::getId)
                    .toList();
            artistDto.setAlbumsId(albumIds);
        }
        return artistDto;
    }

    @Override
    public List<ArtistDto> findAllArtist() {
        List<Artist> artists = artistRepository.findAll();

        return artists.stream()
                .map(this::convertToArtistDto)
                .toList();
    }

    @Override
    public ArtistDto findArtistById(Long id) {
        Artist artist = artistRepository.findArtistById(id);
        return convertToArtistDto(artist);
    }

    @Override
    public void saveArtist(ArtistDto artistDto) {
        Artist artist = convertToArtistEntity(artistDto);
        artistRepository.save(artist);
    }
}
