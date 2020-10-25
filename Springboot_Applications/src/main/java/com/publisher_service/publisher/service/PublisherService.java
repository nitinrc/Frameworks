package com.publisher_service.publisher.service;

import com.publisher_service.model.ResponseDto;

public interface PublisherService {
    ResponseDto getResponseWithName(String name);
    ResponseDto getResponseWithIdAndName(Integer id, String name);
    void updateNameBySave(Integer id, String name);
    void updateNameByQuery(Integer id, String name);
}
