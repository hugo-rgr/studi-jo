package com.studi.jo.controllers;

import com.studi.jo.user.domain.User;
import com.studi.jo.user.domain.Role;
import com.studi.jo.user.domain.UserDTO;
import com.studi.jo.user.infra.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    //@PreAuthorize("!isAuthenticated()")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("Creating new user: {}", userDTO);
        try {
            User newUser = userService.createUser(userDTO, Role.CLIENT);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: Could not create user. " + e.getMessage());
        }
    }

}
