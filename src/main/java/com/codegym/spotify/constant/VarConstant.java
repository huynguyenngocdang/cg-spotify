package com.codegym.spotify.constant;

import java.util.Set;

public interface VarConstant {
    String ROLE_TYPE_USER = "ROLE_USER";
    String ROLE_TYPE_ADMIN = "ROLE_ADMIN";
    String ROLE_TYPE_MODERATOR = "ROLE_MODERATOR";

    Set<String> ALLOWED_EXTENSIONS = Set.of("mp3", "mp4", "wav", "flac", "ogg");
    String SONG_PATH = "src/main/resources/static/assets/song/";
}
