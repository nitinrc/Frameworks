package com.publisher_service.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto implements Serializable {
    private UUID traceId;
    private Integer id;
    private String name;
    private String employee;
}
