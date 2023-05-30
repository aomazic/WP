package com.example.backend.Controller;

import com.example.backend.Service.RegistrationService;
import com.example.backend.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")

public class UserRegistration {

    private final RegistrationService registrationService;
    @Autowired
    public UserRegistration(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String registrationResult = registrationService.register(user);
        return new ResponseEntity<>(registrationResult, HttpStatus.OK);
    }

    @GetMapping("/resend")
    public String resendEmail(@RequestParam("email") String email){


        return registrationService.resendEmail(email);
    }
    @GetMapping("/login")
    public User login(@RequestParam("email") String email, @RequestParam("password") String password){
        return registrationService.login(email, password);
    }
}
