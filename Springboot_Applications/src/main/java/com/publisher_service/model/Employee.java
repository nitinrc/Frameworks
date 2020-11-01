package com.publisher_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "employee", nullable = false)
    private String employee;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    public static EmployeeDto toDto(Employee dao) {
        if (dao == null) {
            return null;
        }

        return EmployeeDto.builder()
                .id(dao.getId())
                .name(dao.getName())
                .employee(dao.getEmployee())
                .salary(dao.getSalary())
                .build();
    }

    public static Employee fromDto(EmployeeDto dto) {
        if (dto == null) {
            return null;
        }

        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .employee(dto.getEmployee())
                .salary(dto.getSalary())
                .build();
    }
}
