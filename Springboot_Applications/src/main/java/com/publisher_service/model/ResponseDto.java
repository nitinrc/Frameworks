package com.publisher_service.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto implements Serializable {
    private Integer id;
    private String name;
    private String employee;
}
