package com.publisher_service.publisher.service;

import com.publisher_service.model.Response;
import com.publisher_service.model.ResponseDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {

    private Response response;

    @Autowired
    public PublisherServiceImpl(Response response) {
        this.response = response;
    }

    public ResponseDto getResponseWithName(String name) {
        Integer id = 1000; //Should ideally come from dao
        return response.toDto(id, name);
    }

    public ResponseDto getResponseWithIdAndName(Integer id, String name) {
        return response.toDto(id, name);
    }
}
