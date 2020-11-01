package com.publisher_service.publisher.compare;

import com.publisher_service.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Service
public class EmployeeSalaryComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee employee1, Employee employee2) {
        return Integer.compare(employee1.getSalary(), employee2.getSalary());
    }

    @Override
    public Comparator<Employee> reversed() {
        return null;
    }

    @Override
    public Comparator<Employee> thenComparing(Comparator<? super Employee> other) {
        return null;
    }

    @Override
    public <U> Comparator<Employee> thenComparing(Function<? super Employee, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return null;
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<Employee> thenComparing(Function<? super Employee, ? extends U> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<Employee> thenComparingInt(ToIntFunction<? super Employee> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<Employee> thenComparingLong(ToLongFunction<? super Employee> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<Employee> thenComparingDouble(ToDoubleFunction<? super Employee> keyExtractor) {
        return null;
    }
}
