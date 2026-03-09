package com.streamio.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchHistoryRequest {

    private Long videoId;
    private Long progressTime;
    private Boolean completed;
}
