package com.publisher_service.publisher;

import com.publisher_service.model.ResponseDto;
import com.publisher_service.publisher.service.PublisherService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        log.info("GET call received with single arg: name: {}", name);
        ResponseDto responseDto = publisherService.getResponseWithName(name);
        return ResponseEntity.ok().body(responseDto);
    }

    @RequestMapping(value = "/get/data2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getData2(@RequestParam(value="id") Integer id,
                                  @RequestParam(value="name") String name) {
        log.info("GET call received with multiple args: id: {}, name: {}", id, name);
        ResponseDto responseDto = publisherService.getResponseWithIdAndName(id, name);
        return ResponseEntity.ok().body(responseDto);
    }

    @RequestMapping(value = "/put/data/{name}", method = RequestMethod.PUT)
    public ResponseEntity putData(@PathVariable("name") String name) {
        log.info("PUT call received with arg: {}", name);
        return ResponseEntity.ok().body("PUT call received with arg: " + name);
    }
}
