package com.codegym.spotify.service;

import com.codegym.spotify.configuration.dto.SongDto;
import java.util.List;

public interface SongService {
    List<SongDto> findAllSongs();
}
