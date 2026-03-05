package com.streaming.videoservice.mappers;

import com.streaming.videoservice.dto.VideoDTO;
import com.streaming.videoservice.entities.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    VideoDTO toDTO(Video video);
    Video toEntity(VideoDTO videoDTO);
}
