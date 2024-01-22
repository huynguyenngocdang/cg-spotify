package com.codegym.spotify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumDto {
    private Long id;
    private String title;
    private byte[] albumImage;
    private Long artistId;
    private List<Long> songsId = new ArrayList<>();
}
