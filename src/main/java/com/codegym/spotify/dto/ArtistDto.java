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
public class ArtistDto {
    private Long id;
    private String name;
    private byte[] artistImage;
    private List<Long> albumsId = new ArrayList<>();
    private Long createdById;
}
