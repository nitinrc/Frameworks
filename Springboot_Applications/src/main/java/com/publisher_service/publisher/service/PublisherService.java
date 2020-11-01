package com.publisher_service.publisher.service;

import com.publisher_service.model.Employee;
import com.publisher_service.model.ResponseDto;
import com.publisher_service.publisher.compare.EmployeeNameCompare;
import com.publisher_service.publisher.compare.EmployeeSalaryCompare;

import java.util.List;

public interface PublisherService {
    ResponseDto getResponseWithName(String name);
    ResponseDto getResponseWithIdAndName(Integer id, String name);
    void updateNameBySave(Integer id, String name);
    void updateNameByQuery(Integer id, String name);
    List<EmployeeSalaryCompare> compareSalary1();
    List<EmployeeNameCompare> compareName1();
    List<Employee> compareSalary2();
    List<Employee> compareName2();
}
