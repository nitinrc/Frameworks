package com.publisher_service.repository;

import com.publisher_service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findById(Integer id);
    Optional<Employee> findByName(String name);
    Optional<Employee> findByIdAndName(Integer id, String name);
    @Modifying
    @Query("update Employee set name = ?2 where id = ?1")
    void updateName(Integer id, String name);

}
