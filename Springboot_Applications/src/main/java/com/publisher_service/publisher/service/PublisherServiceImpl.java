package com.publisher_service.publisher.service;

import com.kafka.Kafka;
import com.publisher_service.messaging.KafkaService;
import com.publisher_service.model.Employee;
import com.publisher_service.model.EmployeeDto;
import com.publisher_service.model.Response;
import com.publisher_service.model.ResponseDto;
import com.publisher_service.publisher.compare.EmployeeNameComparator;
import com.publisher_service.publisher.compare.EmployeeNameCompare;
import com.publisher_service.publisher.compare.EmployeeSalaryComparator;
import com.publisher_service.publisher.compare.EmployeeSalaryCompare;
import com.publisher_service.repository.EmployeeRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {

    private Response response;
    private EmployeeRepository employeeRepository;
    private EmployeeSalaryCompare employeeSalaryCompare;
    private EmployeeNameCompare employeeNameCompare;
    private EmployeeSalaryComparator employeeSalaryComparator;
    private EmployeeNameComparator employeeNameComparator;
    private KafkaService kafkaService;

    @Autowired
    public PublisherServiceImpl(Response response,
                                EmployeeRepository employeeRepository,
                                EmployeeSalaryCompare employeeSalaryCompare,
                                EmployeeNameCompare employeeNameCompare,
                                EmployeeSalaryComparator employeeSalaryComparator,
                                EmployeeNameComparator employeeNameComparator,
                                KafkaService kafkaService) {
        this.response = response;
        this.employeeRepository = employeeRepository;
        this.employeeSalaryCompare = employeeSalaryCompare;
        this.employeeNameCompare = employeeNameCompare;
        this.employeeSalaryComparator = employeeSalaryComparator;
        this.employeeNameComparator = employeeNameComparator;
        this.kafkaService = kafkaService;
    }

    public ResponseDto getResponseWithName(String name) {
        Optional<Employee> employee = employeeRepository.findByName(name);
        EmployeeDto employeeDto = Employee.toDto(employee.get());
        ResponseDto responseDto = response.toDto(employeeDto.getId(), name, employeeDto.getEmployee(), employeeDto.getSalary());
        kafkaService.publishEvent(Kafka.Topics.SINGLE, responseDto);
        return responseDto;
    }

    public ResponseDto getResponseWithIdAndName(Integer id, String name) {
        Optional<Employee> employee = employeeRepository.findByIdAndName(id, name);
        EmployeeDto employeeDto = Employee.toDto(employee.get());
        ResponseDto responseDto = response.toDto(employeeDto.getId(), employeeDto.getName(), employeeDto.getEmployee(), employeeDto.getSalary());
        kafkaService.publishEvent(Kafka.Topics.MULTIPLE, responseDto);
        return responseDto;
    }

    @Transactional
    public void updateNameBySave(Integer id, String name) {
        Optional<Employee> employee = employeeRepository.findById(id);
        log.info("name before update: {}", employee.get().getName());
        Employee employeeUpdated = Employee.builder()
                .id(id)
                .name(name)
                .employee(employee.get().getEmployee())
                .build();
        employeeRepository.save(employeeUpdated);
        log.info("Updated name by save: {}", employeeUpdated.getName());
    }

    @Transactional
    public void updateNameByQuery(Integer id, String name) {
        Optional<Employee> employee = employeeRepository.findById(id);
        log.info("name before update: {}", employee.get().getName());
        employeeRepository.updateName(id, name);
        employee = employeeRepository.findById(id);
        log.info("Updated name by query: {}", employee.get().getName());
    }

    @Transactional
    public List<EmployeeSalaryCompare> compareSalary1() {
        List<Employee> employees = employeeRepository.findAll();
        log.info("employee list from db: {}", employees);
        List<EmployeeSalaryCompare> listEmployeeSalaryCompare = employees.stream().map(e -> {
            EmployeeSalaryCompare ec = new EmployeeSalaryCompare();
            ec.setId(e.getId());
            ec.setName(e.getName());
            ec.setSalary(e.getSalary());
            return ec;
        }).collect(Collectors.toList());
        log.info("employee list before salary comparable sort: {}", listEmployeeSalaryCompare);
        Collections.sort(listEmployeeSalaryCompare);
        log.info("employee list after salary comparable sort: {}", listEmployeeSalaryCompare);
        return listEmployeeSalaryCompare;
    }

    @Transactional
    public List<EmployeeNameCompare> compareName1() {
        List<Employee> employees = employeeRepository.findAll();
        log.info("employee list from db: {}", employees);
        List<EmployeeNameCompare> listEmployeeNameCompare = employees.stream().map(e -> {
            EmployeeNameCompare ec = new EmployeeNameCompare();
            ec.setId(e.getId());
            ec.setName(e.getName());
            return ec;
        }).collect(Collectors.toList());
        log.info("employee list before name comparable sort: {}", listEmployeeNameCompare);
        Collections.sort(listEmployeeNameCompare);
        log.info("employee list after name comparable sort: {}", listEmployeeNameCompare);
        return listEmployeeNameCompare;
    }

    @Transactional
    public List<Employee> compareSalary2() {
        List<Employee> employees = employeeRepository.findAll();
        log.info("employee list from db before salary comparator sort: {}", employees);
        EmployeeSalaryComparator employeeSalaryComparator = new EmployeeSalaryComparator();
        Collections.sort(employees, employeeSalaryComparator);
        log.info("employee list after salary comparator sort: {}", employees);
        return employees;
    }

    @Transactional
    public List<Employee> compareName2() {
        List<Employee> employees = employeeRepository.findAll();
        log.info("employee list from db before name comparator sort: {}", employees);
        EmployeeNameComparator employeeNameComparator = new EmployeeNameComparator();
        Collections.sort(employees, employeeNameComparator);
        log.info("employee list after name comparator sort: {}", employees);
        return employees;
    }
}
