package org.example.spring_security.controller;


import org.example.spring_security.entity.User;
import org.example.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String mail = credentials.get("mail");
        String password = credentials.get("password");

        if (service.checkUserNameExists(mail)) {
            if (service.verifyUser(mail, password)) {
                String token = service.generateToken(mail, password);
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                return ResponseEntity.ok(data);
            } else {
                return ResponseEntity.badRequest().body("Bad login");
            }
        } else {
            return ResponseEntity.badRequest().body("Bad login");
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (service.createUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        }
        return ResponseEntity.badRequest().body("Error");
    }
}
