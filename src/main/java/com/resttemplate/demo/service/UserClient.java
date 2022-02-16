package com.resttemplate.demo.service;

import com.resttemplate.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private HttpHeaders headers;
    private String URL = "http://91.241.64.178:7081/api/users";
    List<String> cookies;
    String cookie;


    @Autowired
    public UserClient(RestTemplate restTemplate, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.headers = headers;
//        HttpHeaders headers1 = restTemplate.headForHeaders(URL);
//        String Cookie = headers1.getFirst("Set-Cookie");
//        this.headers.add("Set-Cookie", Cookie);
//        System.out.println("Cookie = " + Cookie);
//        this.headers.set("Cookie",
//                String.join(";", restTemplate.headForHeaders(URL).get("Set-Cookie")));
    }

    public List<User> getAllUsers() {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(URL, User[].class);
        System.out.println(responseEntity.getHeaders());

//        HttpHeaders headers1 = restTemplate.headForHeaders(URL);
//        cookies = headers1.getFirst("Set-Cookie");
//        this.headers.add("Set-Cookie", cookies);
//        System.out.println("Cookie = " + cookies);

        System.out.println();
        System.out.println();
        cookies = responseEntity.getHeaders().get("Set-Cookie");
        cookies.forEach(System.out::println);
        cookie = cookies.toString().substring(1, cookies.toString().indexOf(';'));
        System.out.println(cookie);
        System.out.println();
        System.out.println();

        return Arrays.asList(responseEntity.getBody());
/*        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() { }
                );
        System.out.println(responseEntity.getHeaders());
        return responseEntity.getBody();*/
    }

    public ResponseEntity<String> addUser(User user) {
//        User user = new User(3L, "James", "Brown", (byte) 33);
//        HttpEntity<User> entity = new HttpEntity<>(user, headers);
//        ResponseEntity<User> entity = new ResponseEntity<>(user, headers, HttpStatus.CREATED);

//        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        headers.set("Set-Cookie", cookie);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        System.out.println("Cookie = " + cookies);
        System.out.println("сейчас добавим новго юзера");
        System.out.println(entity.getBody());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, entity, String.class);
//        this.headers = responseEntity.getHeaders();
        System.out.println(this.headers);
        return responseEntity;

//        return restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
//        return restTemplate.postForEntity(URL, entity, String.class);
    }

    public ResponseEntity<String> updateUser(User user) {
//        User user = new User(3L, "Thomas", "Shelby", (byte) 33);
//        HttpEntity<User> entity = new HttpEntity<>(user, headers);
//        ResponseEntity<User> entity = new ResponseEntity<>(user, headers, HttpStatus.ACCEPTED);
//        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));

        Arrays.asList(restTemplate.getForEntity(URL, User[].class).getBody()).forEach(System.out::println);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        System.out.println("Cookie = " + cookies);
        System.out.println("сейчас изменим юзера");
        System.out.println(entity.getBody());
        System.out.println(this.headers);
        return restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
    }

    public ResponseEntity<String> deleteUser(Long id) {
//    public void deleteUser(Long id) {
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("id", id);
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//        ResponseEntity<String> entity = new ResponseEntity<>(null, headers,HttpStatus.ACCEPTED);
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        System.out.println("Cookie = " + cookies);
        System.out.println("сейчас удалим юзера");
        System.out.println(entity.getBody());
//        restTemplate.delete(URL, uriVariables);
        return restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class, uriVariables);
    }
}
