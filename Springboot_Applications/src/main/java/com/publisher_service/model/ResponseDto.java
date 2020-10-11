package com.publisher_service.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResponseDto {
    private Integer id;
    private String name;
    private String employee;
}
