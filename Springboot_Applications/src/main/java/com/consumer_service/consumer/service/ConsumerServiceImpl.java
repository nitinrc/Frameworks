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

    @Value("${url.server}")
    private String server;

    @Value("${url.single}")
    private String urlSingle;

    @Value("${url.multiple}")
    private String urlMultiple;

    @Value("${url.update-name-by-save}")
    private String urlUpdateNameBySave;

    @Value("${url.update-name-by-query}")
    private String urlUpdateNameByQuery;

    private RestTemplateConfig restTemplateConfig;
    private Response response;

    @Autowired
    public ConsumerServiceImpl(RestTemplateConfig restTemplateConfig, Response response) {
        this.restTemplateConfig = restTemplateConfig;
        this.response = response;
    }

    public ResponseEntity<String> getResponseWithName(String name) {
        log.info("URL Single: {}", server + urlSingle);
        ResponseEntity<String> responseEntity = restTemplateConfig.restTemplate()
                .getForEntity(server + urlSingle + "nitin", String.class);
        return responseEntity;
    }

    public ResponseEntity<String> getResponseWithIdAndName(Integer id, String name) {
        log.info("URL Multiple: {}", server + urlMultiple);
        ResponseEntity<String> responseEntity = restTemplateConfig.restTemplate()
                .getForEntity(server + urlMultiple + "id=1000&name=nitin", String.class);
        return responseEntity;
    }

    public ResponseEntity<String> updateNameBySave(Integer id, String name) {
        log.info("URL Update name by save: {}", server + urlUpdateNameBySave);
        ResponseEntity<String> responseEntity = restTemplateConfig.restTemplate()
                .getForEntity(server + urlUpdateNameBySave + "id=1000&name=nitin", String.class);
        return responseEntity;
    }

    public ResponseEntity<String> updateNameByQuery(Integer id, String name) {
        log.info("URL Update name by query: {}", server + urlUpdateNameByQuery);
        ResponseEntity<String> responseEntity = restTemplateConfig.restTemplate()
                .getForEntity(server + urlUpdateNameByQuery + "id=1000&name=nitin", String.class);
        return responseEntity;
    }
}
