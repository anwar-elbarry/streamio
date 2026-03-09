package com.streamio.user_service.client;

import com.streamio.user_service.dto.VideoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "video-service", url = "${services.video-service.url}")
public interface VideoServiceClient {

    @GetMapping("/api/videos/{id}")
    VideoDto getVideoById(@PathVariable("id") Long id);
}
