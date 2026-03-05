package com.streamio.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchlistResponse {

    private Long id;
    private Long userId;
    private Long videoId;
    private LocalDateTime addedAt;
}
