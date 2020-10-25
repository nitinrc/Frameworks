package common;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.var;

import java.util.Map;

public class RestAssuredExtension {

    public static RequestSpecification request;

    public RestAssuredExtension(String uri, Integer port) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(uri);
        requestSpecBuilder.setPort(port);
        var requestSpec = requestSpecBuilder.build();
        request = RestAssured.given(requestSpec);
    }

    public static Response get(String url) {
        return request.get(url);
    }

    public static Response getWithPathParameter(String url, Map<String, Object> pathParams) {
        request.pathParams(pathParams);
        return request.get(url);
    }

    public static Response getWithQueryParameter(String url, Map<String, Object> queryParams) {
        request.queryParams(queryParams);
        return request.get(url);
    }

    public static Response postWithBody(String url, Map<String, Object> body) {
        request.contentType("application/json");
        request.accept("application/json");
        request.body(body);
        return request.post(url);
    }

    public static Response postWithBodyAndPathParams(String url, Map<String, String> pathParams, String body) {
        request.contentType("application/json");
        request.accept("application/json");
        request.pathParams(pathParams);
        request.body(body);
        return request.post(url);
    }

    public static Response putWithBody(String url, Map<String, Object> body) {
        request.contentType("application/json");
        request.accept("application/json");
        request.body(body);
        return request.put(url);
    }

    public static Response putWithBodyAndPathParams(String url, Map<String, String> pathParams, Map<String, String> body) {
        request.contentType("application/json");
        request.accept("application/json");
        request.pathParams(pathParams);
        request.body(body);
        return request.put(url);
    }

    public static Response deleteWithPathParams(String url, Map<String, Object> pathParams) {
        request.pathParams(pathParams);
        request.accept("application/json");
        return request.delete(url);
    }

    public static Response deleteWithPathAndQueryParams(String url, Map<String, Object> pathParams, Map<String, Object> queryParams) {
        request.pathParams(pathParams);
        request.queryParams(queryParams);
        request.accept("application/json");
        return request.delete(url);
    }
}
