package com.codegym.spotify.service;

import java.util.List;

public interface LyricService {
    List<String> getSongLyrics(String band, String songTitle);
}
