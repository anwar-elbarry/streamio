package com.streaming.videoservice.dto;

import com.streaming.videoservice.entities.Category;
import com.streaming.videoservice.entities.VideoType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class VideoDTO {
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private int duration;
    private int releaseYear;
    private VideoType type;
    private Category category;
    private double rating;
    private String director;
    private String cast;
}
