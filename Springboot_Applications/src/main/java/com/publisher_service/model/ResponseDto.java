package com.publisher_service.model;

import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto implements Serializable {
    private UUID traceId;
    private ZonedDateTime time;
    private Integer id;
    private String name;
    private String employee;
    private Integer salary;
}
