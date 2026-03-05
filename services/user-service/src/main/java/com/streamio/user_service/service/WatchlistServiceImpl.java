package com.streamio.user_service.service;

import com.streamio.user_service.dto.WatchlistResponse;
import com.streamio.user_service.entity.User;
import com.streamio.user_service.entity.Watchlist;
import com.streamio.user_service.exception.UserNotFoundException;
import com.streamio.user_service.repository.UserRepository;
import com.streamio.user_service.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;

    @Override
    public WatchlistResponse addToWatchlist(Long userId, Long videoId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Check if already in watchlist
        if (watchlistRepository.existsByUserIdAndVideoId(userId, videoId)) {
            Watchlist existing = watchlistRepository.findByUserIdAndVideoId(userId, videoId).get();
            return mapToResponse(existing);
        }

        Watchlist watchlist = Watchlist.builder()
                .user(user)
                .videoId(videoId)
                .addedAt(LocalDateTime.now())
                .build();

        Watchlist saved = watchlistRepository.save(watchlist);
        return mapToResponse(saved);
    }

    @Override
    public void removeFromWatchlist(Long userId, Long videoId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        watchlistRepository.deleteByUserIdAndVideoId(userId, videoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WatchlistResponse> getUserWatchlist(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return watchlistRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInWatchlist(Long userId, Long videoId) {
        return watchlistRepository.existsByUserIdAndVideoId(userId, videoId);
    }

    private WatchlistResponse mapToResponse(Watchlist watchlist) {
        return WatchlistResponse.builder()
                .id(watchlist.getId())
                .userId(watchlist.getUser().getId())
                .videoId(watchlist.getVideoId())
                .addedAt(watchlist.getAddedAt())
                .build();
    }
}
