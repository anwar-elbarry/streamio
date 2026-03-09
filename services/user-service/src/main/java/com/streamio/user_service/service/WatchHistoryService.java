package com.streamio.user_service.service;

import com.streamio.user_service.dto.WatchHistoryRequest;
import com.streamio.user_service.dto.WatchHistoryResponse;
import com.streamio.user_service.dto.WatchStatsResponse;

import java.util.List;

public interface WatchHistoryService {

    WatchHistoryResponse recordWatch(Long userId, WatchHistoryRequest request);

    WatchHistoryResponse updateProgress(Long userId, Long videoId, Long progressTime, Boolean completed);

    List<WatchHistoryResponse> getUserWatchHistory(Long userId);

    WatchHistoryResponse getWatchProgress(Long userId, Long videoId);

    WatchStatsResponse getUserWatchStats(Long userId);
}
