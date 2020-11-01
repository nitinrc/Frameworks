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
public class EmployeeNameCompare implements Comparable<EmployeeNameCompare> {

    private Integer id;
    private String name;

    @Override
    public int compareTo(EmployeeNameCompare otherEmployee) {
        return getName().compareTo(otherEmployee.getName());
    }
}
