package com.resttemplate.demo;

import com.resttemplate.demo.Entity.User;
import com.resttemplate.demo.service.UserClient;
import com.resttemplate.demo.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;

@SpringBootApplication
public class RestTemplateApplication {

    public static void main(String[] args) {
//        SpringApplication.run(RestTemplateApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(Config.class, args);
        UserClient userClient = context.getBean("userClient", UserClient.class);
        System.out.println(userClient.getAllUsers());
        System.out.println("--------------------- 1ая часть: " + userClient.addUser(new User(3L, "James", "Brown", (byte) 33)).getBody());
//        System.out.println(userClient.getAllUsers());
        System.out.println("--------------------- 2ая часть: " + userClient.updateUser(new User(3L, "Thomas", "Shelby", (byte) 33)).getBody());
//        System.out.println(userClient.getAllUsers());
        System.out.println("--------------------- 3ая часть: " + userClient.deleteUser(3L).getBody());
//        System.out.println(userClient.getAllUsers());
    }

}
