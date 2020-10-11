package com.publisher_service.model;

import lombok.*;
import org.springframework.stereotype.Service;

@Data
@Builder
@EqualsAndHashCode
@ToString
@Service
public class Response {

    public ResponseDto toDto(String name) {
        return ResponseDto.builder()
                .id(1000)
                .name(name)
                .build();
    }
}
