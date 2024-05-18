package com.studi.jo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class HomeController {

    public static void main(String[] args) {
        SpringApplication.run(com.studi.jo.HomeController.class, args);
    }

    @RequestMapping("/")
    public String helloWorld() {
        return "hello";
    }

}
