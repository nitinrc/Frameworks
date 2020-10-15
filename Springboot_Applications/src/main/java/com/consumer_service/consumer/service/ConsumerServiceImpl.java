package com.consumer_service.consumer.service;

import com.publisher_service.model.Response;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

    @Value("${url.single}")
    private String urlSingle;

    @Value("${url.multiple}")
    private String urlMultiple;

    private RestService restService;
    private Response response;

    @Autowired
    public ConsumerServiceImpl(RestService restService, Response response) {
        this.restService = restService;
        this.response = response;
    }

    public ResponseEntity<String> getResponseWithName(String name) {
        log.info("URL Single: {}", urlSingle);
        ResponseEntity<String> responseEntity = restService.restTemplate().getForEntity(urlSingle + "nitin", String.class);
        return responseEntity;
    }

    public ResponseEntity<String> getResponseWithIdAndName(Integer id, String name) {
        log.info("URL Multiple: {}", urlMultiple);
        ResponseEntity<String> responseEntity = restService.restTemplate().getForEntity(urlMultiple + "id=10&name=nitin", String.class);
        return responseEntity;
    }
}
