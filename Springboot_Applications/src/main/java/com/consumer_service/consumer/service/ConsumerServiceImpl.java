package com.consumer_service.consumer.service;

import com.configs.rest_template.RestTemplateConfig;
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

    private RestTemplateConfig restTemplateConfig;
    private Response response;

    @Autowired
    public ConsumerServiceImpl(RestTemplateConfig restTemplateConfig, Response response) {
        this.restTemplateConfig = restTemplateConfig;
        this.response = response;
    }

    public ResponseEntity<String> getResponseWithName(String name) {
        log.info("URL Single: {}", urlSingle);
        ResponseEntity<String> responseEntity = restTemplateConfig.restTemplate().getForEntity(urlSingle + "nitin", String.class);
        return responseEntity;
    }

    public ResponseEntity<String> getResponseWithIdAndName(Integer id, String name) {
        log.info("URL Multiple: {}", urlMultiple);
        ResponseEntity<String> responseEntity = restTemplateConfig.restTemplate().getForEntity(urlMultiple + "id=10&name=nitin", String.class);
        return responseEntity;
    }
}
