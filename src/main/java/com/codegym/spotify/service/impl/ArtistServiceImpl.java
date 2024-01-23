package com.codegym.spotify.service.impl;

import com.codegym.spotify.dto.ArtistDto;
import com.codegym.spotify.entity.Album;
import com.codegym.spotify.entity.Artist;
import com.codegym.spotify.entity.UserEntity;
import com.codegym.spotify.repository.AlbumRepository;
import com.codegym.spotify.repository.ArtistRepository;
import com.codegym.spotify.repository.UserRepository;
import com.codegym.spotify.security.SecurityUtil;
import com.codegym.spotify.service.ArtistService;
import com.codegym.spotify.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final ModelMapper modelMapper;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    public ArtistServiceImpl(ArtistRepository artistRepository,
                             ModelMapper modelMapper,
                             AlbumRepository albumRepository,
                             UserRepository userRepository,
                             UserService userService) {
        this.artistRepository = artistRepository;
        this.modelMapper = modelMapper;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    private Artist convertToArtistEntity(ArtistDto artistDto) {
        Artist artist = modelMapper.map(artistDto, Artist.class);
        if (artistDto.getAlbumsId() != null && !artistDto.getAlbumsId().isEmpty()) {
            List<Album> albums = albumRepository.findAllById(artistDto.getAlbumsId());
            artist.setAlbums(albums);
            albums.forEach(album -> album.setArtist(artist));
        }


        if (artistDto.getCreatedById() != null) {
          userRepository.findById(artistDto.getCreatedById()).ifPresent(artist::setCreatedBy);
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

        if (artist.getCreatedBy() != null) {
            artistDto.setCreatedById(artistDto.getCreatedById());
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
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userService.findByUsername(username);
        Artist artist = convertToArtistEntity(artistDto);
        artist.setCreatedBy(user);
        artistRepository.save(artist);
    }
}
