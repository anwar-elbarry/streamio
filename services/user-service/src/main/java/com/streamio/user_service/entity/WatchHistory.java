package com.streamio.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "watch_histories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Column(name = "watched_at", nullable = false)
    @Builder.Default
    private LocalDateTime watchedAt = LocalDateTime.now();

    @Column(name = "progress_time")
    @Builder.Default
    private Long progressTime = 0L;

    @Column(nullable = false)
    @Builder.Default
    private Boolean completed = false;
}
