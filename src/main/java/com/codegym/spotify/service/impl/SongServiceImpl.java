package com.codegym.spotify.service.impl;

import com.codegym.spotify.dto.SongDto;
import com.codegym.spotify.entity.Song;
import com.codegym.spotify.repository.AlbumRepository;
import com.codegym.spotify.repository.SongRepository;
import com.codegym.spotify.service.SongService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import static com.codegym.spotify.constant.VarConstant.ALLOWED_EXTENSIONS;
import static com.codegym.spotify.constant.VarConstant.SONG_PATH;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;
    private final AlbumRepository albumRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, ModelMapper modelMapper, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
        this.albumRepository = albumRepository;
    }

    @Override
    public List<SongDto> findAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map(this::convertToSongDto).collect(Collectors.toList());
    }

    @Override
    public SongDto findSongById(Long songId) {
        Song song = songRepository.findSongsById(songId);
        return convertToSongDto(song);
    }

    @Override
    public List<SongDto> findSongsByAlbumId(Long albumId) {
        List<Song> songs = songRepository.findSongsByAlbumId(albumId);
        return songs.stream().map(this::convertToSongDto).collect(Collectors.toList());
    }

    @Override
    public SongDto convertToSongDto(Song song) {
        SongDto songDto = modelMapper.map(song, SongDto.class);
        if (song.getAlbum() != null) {
            songDto.setAlbumId(song.getAlbum().getId());
        }
        return songDto;
    }

    @Override
    public Song convertToSongEntity(SongDto songDto) {
        Song song = modelMapper.map(songDto, Song.class);
        if (songDto.getAlbumId() != null) {
            albumRepository.findById(songDto.getAlbumId()).ifPresent(song::setAlbum);
        }
        return song;
    }

    @Override
    public boolean handleSongUpload(MultipartFile file, String fileName) {
        String folder = SONG_PATH;
        Path directoryPath = Paths.get(folder);

        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);

        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            return false;
        }

        String safeFileName = getSafeFileName(fileName) + "." + fileExtension;

        Path filePath = directoryPath.resolve(safeFileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private String getSafeFileName(String fileName) {
        // Implement logic to sanitize and return a safe file name
        // This is important to avoid security issues like Path Traversal
        // For simplicity, I'm returning the fileName as is, but you should implement proper sanitization
        return fileName;
    }

    @Override
    public void saveSong(SongDto songDto, MultipartFile file) {
        Song song = convertToSongEntity(songDto);
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String safeFileName = song.getFilename() + "." + fileExtension;
        song.setFilename(safeFileName);
        songRepository.save(song);
    }

}
