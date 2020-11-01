package com.publisher_service.publisher;

import com.publisher_service.model.Employee;
import com.publisher_service.model.ResponseDto;
import com.publisher_service.publisher.compare.EmployeeNameCompare;
import com.publisher_service.publisher.compare.EmployeeSalaryCompare;
import com.publisher_service.publisher.service.PublisherService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@NoArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/publisher", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublisherController {

    private PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @RequestMapping(value = "/get/data1/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getData1(@PathVariable("name") String name) {
        log.info("GET call received with single arg -> name: {}", name);
        try {
            ResponseDto responseDto = publisherService.getResponseWithName(name);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }

    @RequestMapping(value = "/get/data2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getData2(@RequestParam(value="id") Integer id,
                                  @RequestParam(value="name") String name) {
        log.info("GET call received with multiple args -> id: {}, name: {}", id, name);
        try {
            ResponseDto responseDto = publisherService.getResponseWithIdAndName(id, name);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }

    @RequestMapping(value = "/put/save", method = RequestMethod.GET)
    public ResponseEntity updateNameBySave(@RequestParam(value="id") Integer id,
                                           @RequestParam(value="name") String name) {
        log.info("GET call received to update name by save with args -> id: {}, name: {}", id, name);
        try {
            publisherService.updateNameBySave(id, name);
            return ResponseEntity.ok().body("Update name to: " + name + " by save successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }

    @RequestMapping(value = "/put/query", method = RequestMethod.GET)
    public ResponseEntity updateNameByQuery(@RequestParam(value="id") Integer id,
                                  @RequestParam(value="name") String name) {
        log.info("GET call received to update name by query with args -> id: {}, name: {}", id, name);
        try {
            publisherService.updateNameByQuery(id, name);
            return ResponseEntity.ok().body("Update name to: " + name + " by query successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }

    @RequestMapping(value = "/compare/salary1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity compareSalary1() {
        log.info("GET call received for comparing salary by comparable");
        try {
            List<EmployeeSalaryCompare> listEmployeeSalaryCompare = publisherService.compareSalary1();
            return ResponseEntity.ok().body(listEmployeeSalaryCompare);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }

    @RequestMapping(value = "/compare/name1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity compareName1() {
        log.info("GET call received for comparing name by comparable");
        try {
            List<EmployeeNameCompare> listEmployeeNameCompare = publisherService.compareName1();
            return ResponseEntity.ok().body(listEmployeeNameCompare);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }

    @RequestMapping(value = "/compare/salary2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity compareSalary2() {
        log.info("GET call received for comparing salary by comparator");
        try {
            List<Employee> employees = publisherService.compareSalary2();
            return ResponseEntity.ok().body(employees);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }

    @RequestMapping(value = "/compare/name2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity compareName2() {
        log.info("GET call received for comparing name by comparator");
        try {
            List<Employee> employees = publisherService.compareName2();
            return ResponseEntity.ok().body(employees);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }
}
