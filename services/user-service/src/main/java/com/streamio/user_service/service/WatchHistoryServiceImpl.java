package com.streamio.user_service.service;

import com.streamio.user_service.client.VideoServiceClient;
import com.streamio.user_service.dto.VideoDto;
import com.streamio.user_service.dto.WatchHistoryRequest;
import com.streamio.user_service.dto.WatchHistoryResponse;
import com.streamio.user_service.dto.WatchStatsResponse;
import com.streamio.user_service.entity.User;
import com.streamio.user_service.entity.WatchHistory;
import com.streamio.user_service.exception.UserNotFoundException;
import com.streamio.user_service.exception.VideoServiceException;
import com.streamio.user_service.repository.UserRepository;
import com.streamio.user_service.repository.WatchHistoryRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WatchHistoryServiceImpl implements WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final UserRepository userRepository;
    private final VideoServiceClient videoServiceClient;

    @Override
    public WatchHistoryResponse recordWatch(Long userId, WatchHistoryRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Valider que la vidéo existe dans le video-service
        validateVideoExists(request.getVideoId());

        Optional<WatchHistory> existing = watchHistoryRepository.findByUserIdAndVideoId(userId, request.getVideoId());

        WatchHistory watchHistory;
        if (existing.isPresent()) {
            watchHistory = existing.get();
            watchHistory.setWatchedAt(LocalDateTime.now());
            if (request.getProgressTime() != null) {
                watchHistory.setProgressTime(request.getProgressTime());
            }
            if (request.getCompleted() != null) {
                watchHistory.setCompleted(request.getCompleted());
            }
        } else {
            watchHistory = WatchHistory.builder()
                    .user(user)
                    .videoId(request.getVideoId())
                    .watchedAt(LocalDateTime.now())
                    .progressTime(request.getProgressTime() != null ? request.getProgressTime() : 0L)
                    .completed(request.getCompleted() != null ? request.getCompleted() : false)
                    .build();
        }

        WatchHistory saved = watchHistoryRepository.save(watchHistory);
        return mapToResponse(saved);
    }

    @Override
    public WatchHistoryResponse updateProgress(Long userId, Long videoId, Long progressTime, Boolean completed) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        WatchHistory watchHistory = watchHistoryRepository.findByUserIdAndVideoId(userId, videoId)
                .orElseThrow(() -> new RuntimeException("Watch history not found for user " + userId + " and video " + videoId));

        if (progressTime != null) watchHistory.setProgressTime(progressTime);
        if (completed != null) watchHistory.setCompleted(completed);
        watchHistory.setWatchedAt(LocalDateTime.now());

        return mapToResponse(watchHistoryRepository.save(watchHistory));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WatchHistoryResponse> getUserWatchHistory(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return watchHistoryRepository.findByUserIdOrderByWatchedAtDesc(userId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WatchHistoryResponse getWatchProgress(Long userId, Long videoId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return watchHistoryRepository.findByUserIdAndVideoId(userId, videoId)
                .map(this::mapToResponse)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public WatchStatsResponse getUserWatchStats(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        long totalVideos     = watchHistoryRepository.countByUserId(userId);
        long completedVideos = watchHistoryRepository.countCompletedByUserId(userId);
        Long totalTime       = watchHistoryRepository.getTotalWatchTimeByUserId(userId);
        long watchTimeSeconds = totalTime != null ? totalTime : 0L;

        return WatchStatsResponse.builder()
                .userId(userId)
                .totalVideosWatched(totalVideos)
                .completedVideos(completedVideos)
                .inProgressVideos(totalVideos - completedVideos)
                .totalWatchTimeSeconds(watchTimeSeconds)
                .formattedWatchTime(formatWatchTime(watchTimeSeconds))
                .build();
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private void validateVideoExists(Long videoId) {
        try {
            VideoDto video = videoServiceClient.getVideoById(videoId);
            log.debug("Video found: {} - {}", video.getId(), video.getTitle());
        } catch (FeignException.NotFound e) {
            throw new VideoServiceException("Video not found with id: " + videoId);
        } catch (FeignException e) {
            log.warn("video-service unavailable, skipping validation for videoId={}", videoId);
            // On continue sans bloquer si le service est indisponible
        }
    }

    private WatchHistoryResponse mapToResponse(WatchHistory wh) {
        return WatchHistoryResponse.builder()
                .id(wh.getId())
                .userId(wh.getUser().getId())
                .videoId(wh.getVideoId())
                .watchedAt(wh.getWatchedAt())
                .progressTime(wh.getProgressTime())
                .completed(wh.getCompleted())
                .build();
    }

    private String formatWatchTime(long seconds) {
        return String.format("%dh %dm %ds", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }
}
