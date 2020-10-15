package com.consumer_service.consumer.service;

import org.springframework.http.ResponseEntity;

public interface ConsumerService {
    ResponseEntity<String> getResponseWithName(String name);
    ResponseEntity<String> getResponseWithIdAndName(Integer id, String name);
}
