package com.codegym.spotify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDto {
    private Long id;
    private String title;
    private String filename;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Long albumId;
}

