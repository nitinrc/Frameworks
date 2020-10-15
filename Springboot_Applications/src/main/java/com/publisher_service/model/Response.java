package com.publisher_service.model;

import lombok.*;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
                .time(ZonedDateTime.now(ZoneId.of("UTC")))
                .id(id)
                .name(name)
                .employee("YES")
                .build();
    }
}
