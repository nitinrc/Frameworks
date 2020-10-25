package com.publisher_service.publisher.service;

import com.kafka.Kafka;
import com.publisher_service.messaging.KafkaService;
import com.publisher_service.model.Employee;
import com.publisher_service.model.EmployeeDto;
import com.publisher_service.model.Response;
import com.publisher_service.model.ResponseDto;
import com.publisher_service.repository.EmployeeRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@NoArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {

    private Response response;
    private EmployeeRepository employeeRepository;
    private KafkaService kafkaService;

    @Autowired
    public PublisherServiceImpl(Response response, EmployeeRepository employeeRepository, KafkaService kafkaService) {
        this.response = response;
        this.employeeRepository = employeeRepository;
        this.kafkaService = kafkaService;
    }

    public ResponseDto getResponseWithName(String name) {
        Optional<Employee> employee = employeeRepository.findByName(name);
        EmployeeDto employeeDto = Employee.toDto(employee.get());
        ResponseDto responseDto = response.toDto(employeeDto.getId(), name, employeeDto.getEmployee());
        kafkaService.publishEvent(Kafka.Topics.SINGLE, responseDto);
        return responseDto;
    }

    public ResponseDto getResponseWithIdAndName(Integer id, String name) {
        Optional<Employee> employee = employeeRepository.findByIdAndName(id, name);
        EmployeeDto employeeDto = Employee.toDto(employee.get());
        ResponseDto responseDto = response.toDto(employeeDto.getId(), employeeDto.getName(), employeeDto.getEmployee());
        kafkaService.publishEvent(Kafka.Topics.MULTIPLE, responseDto);
        return responseDto;
    }

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
}
