package com.publisher_service.model;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode
@ToString
@Service
public class Response {

    public ResponseDto toDto(Integer id, String name) {
        return ResponseDto.builder()
                .traceId(UUID.randomUUID())
                .id(id)
                .name(name)
                .employee("YES")
                .build();
    }
}
