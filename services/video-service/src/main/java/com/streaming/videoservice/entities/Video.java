package com.streaming.videoservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000) // Pour les descriptions longues
    private String description;

    private String thumbnailUrl;
    private String trailerUrl; // URL YouTube embed demandée
    private int duration;
    private int releaseYear;

    @Enumerated(EnumType.STRING)
    private VideoType type;

    @Enumerated(EnumType.STRING)
    private Category category;

    private double rating;
    private String director;
    private String cast;
}
