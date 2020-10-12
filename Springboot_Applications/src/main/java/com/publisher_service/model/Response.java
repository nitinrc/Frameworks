package com.publisher_service.model;

import lombok.*;
import org.springframework.stereotype.Service;

@Data
@Builder
@EqualsAndHashCode
@ToString
@Service
public class Response {

    public ResponseDto toDto(Integer id, String name) {
        return ResponseDto.builder()
                .id(id)
                .name(name)
                .employee("YES")
                .build();
    }
}
