package com.resttemplate.demo.service;

import com.resttemplate.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private HttpHeaders headers;
    private String URL = "http://91.241.64.178:7081/api/users";
    List<String> cookies;
    String cookie;
    String cookieName = "Cookie";


    @Autowired
    public UserClient(RestTemplate restTemplate, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.headers = headers;
    }

    public List<User> getAllUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(URL, User[].class);
        cookies = responseEntity.getHeaders().get("Set-Cookie");
        cookie = cookies.toString().substring(1, cookies.toString().indexOf(';'));
        return Arrays.asList(responseEntity.getBody());
    }

    public ResponseEntity<String> addUser(User user) {
        headers.set(cookieName, cookie);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, entity, String.class);
        return responseEntity;
    }

    public ResponseEntity<String> updateUser(User user) {
        headers.set(cookieName, cookie);
//        Arrays.asList(restTemplate.getForEntity(URL, User[].class).getBody()).forEach(System.out::println);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
    }

    public ResponseEntity<String> deleteUser(Long id) {
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("id", id);
        headers.set(cookieName, cookie);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class, uriVariables);
    }
}
