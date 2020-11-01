package com.publisher_service.publisher.compare;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Service
public class EmployeeSalaryCompare implements Comparable<EmployeeSalaryCompare> {

    private Integer id;
    private String name;
    private Integer salary;

    @Override
    public int compareTo(EmployeeSalaryCompare otherEmployee) {
        return Integer.compare(getSalary(), otherEmployee.getSalary());
    }
}
