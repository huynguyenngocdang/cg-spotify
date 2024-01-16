package com.codegym.spotify.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class SongDto {
    private Long id;
    private String title;
    private String photoUrl;
    private Long artistId;
    private Long albumId;
    private Long songGenreId;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
