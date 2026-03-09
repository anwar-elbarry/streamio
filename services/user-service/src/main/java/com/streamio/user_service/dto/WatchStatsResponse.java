package com.streamio.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchStatsResponse {

    private Long userId;
    private long totalVideosWatched;
    private long completedVideos;
    private long inProgressVideos;
    private long totalWatchTimeSeconds;
    private String formattedWatchTime;
}
