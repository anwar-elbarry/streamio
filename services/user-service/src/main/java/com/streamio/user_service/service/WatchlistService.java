package com.streamio.user_service.service;

import com.streamio.user_service.dto.WatchlistResponse;

import java.util.List;

public interface WatchlistService {

    WatchlistResponse addToWatchlist(Long userId, Long videoId);

    void removeFromWatchlist(Long userId, Long videoId);

    List<WatchlistResponse> getUserWatchlist(Long userId);

    boolean isInWatchlist(Long userId, Long videoId);
}
