package com.streamio.user_service.controller;

import com.streamio.user_service.dto.WatchHistoryRequest;
import com.streamio.user_service.dto.WatchHistoryResponse;
import com.streamio.user_service.dto.WatchStatsResponse;
import com.streamio.user_service.service.WatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    @PostMapping
    public ResponseEntity<WatchHistoryResponse> recordWatch(
            @PathVariable Long userId,
            @RequestBody WatchHistoryRequest request) {
        WatchHistoryResponse response = watchHistoryService.recordWatch(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{videoId}")
    public ResponseEntity<WatchHistoryResponse> updateProgress(
            @PathVariable Long userId,
            @PathVariable Long videoId,
            @RequestParam(required = false) Long progressTime,
            @RequestParam(required = false) Boolean completed) {
        WatchHistoryResponse response = watchHistoryService.updateProgress(userId, videoId, progressTime, completed);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<WatchHistoryResponse>> getUserWatchHistory(@PathVariable Long userId) {
        List<WatchHistoryResponse> history = watchHistoryService.getUserWatchHistory(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<WatchHistoryResponse> getWatchProgress(
            @PathVariable Long userId,
            @PathVariable Long videoId) {
        WatchHistoryResponse response = watchHistoryService.getWatchProgress(userId, videoId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<WatchStatsResponse> getUserWatchStats(@PathVariable Long userId) {
        WatchStatsResponse stats = watchHistoryService.getUserWatchStats(userId);
        return ResponseEntity.ok(stats);
    }
}
