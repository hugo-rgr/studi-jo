package com.studi.jo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class HomeController {

    public static void main(String[] args) {
        SpringApplication.run(com.studi.jo.HomeController.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    //@GetMapping("/admin")
    //public String adminPage() {
    //    return "index";
    //}

}
