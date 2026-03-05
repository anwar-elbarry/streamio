package com.streamio.user_service.controller;

import com.streamio.user_service.dto.WatchlistResponse;
import com.streamio.user_service.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/{userId}/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping("/{videoId}")
    public ResponseEntity<WatchlistResponse> addToWatchlist(
            @PathVariable Long userId,
            @PathVariable Long videoId) {
        WatchlistResponse response = watchlistService.addToWatchlist(userId, videoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> removeFromWatchlist(
            @PathVariable Long userId,
            @PathVariable Long videoId) {
        watchlistService.removeFromWatchlist(userId, videoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WatchlistResponse>> getUserWatchlist(@PathVariable Long userId) {
        List<WatchlistResponse> watchlist = watchlistService.getUserWatchlist(userId);
        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/{videoId}/check")
    public ResponseEntity<Map<String, Boolean>> isInWatchlist(
            @PathVariable Long userId,
            @PathVariable Long videoId) {
        boolean inWatchlist = watchlistService.isInWatchlist(userId, videoId);
        return ResponseEntity.ok(Map.of("inWatchlist", inWatchlist));
    }
}
