package com.streamio.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDto {

    private Long id;
    private String title;
    private String description;
    private Long durationSeconds;
    private String status;
}
