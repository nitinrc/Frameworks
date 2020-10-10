package com.sample_service.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController {
//    @Value("${server.address}")
//    private String serverAddress;
//
//    @Value("${server.port}")
//    private String serverPort;

    public SampleController() {

    }

    @RequestMapping(value = "/get/data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getSample() {

        return ResponseEntity.ok().body("SUCCESS");
    }

}
