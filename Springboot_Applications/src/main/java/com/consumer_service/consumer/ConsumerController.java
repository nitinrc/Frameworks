package com.consumer_service.consumer;

import com.consumer_service.consumer.service.ConsumerService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/consumer", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsumerController {

    private ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @RequestMapping(value = "/get/data1/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getData1(@PathVariable("name") String name) {
        log.info("GET call received with single arg: name: {}", name);
        try {
            ResponseEntity<String> responseDto = consumerService.getResponseWithName(name);
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
        log.info("GET call received with multiple args: id: {}, name: {}", id, name);
        try {
            ResponseEntity<String> responseDto = consumerService.getResponseWithIdAndName(id, name);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }

    @RequestMapping(value = "/put/data/{name}", method = RequestMethod.PUT)
    public ResponseEntity putData(@PathVariable("name") String name) {
        log.info("PUT call received with arg: {}", name);
        try {
            return ResponseEntity.ok().body("PUT call received with arg: " + name);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There was an error processing the request.");
        }
    }
}
