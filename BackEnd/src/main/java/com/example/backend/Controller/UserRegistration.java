package com.example.backend.Controller;

import com.example.backend.Service.RegistrationService;
import com.example.backend.model.User.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")

public class UserRegistration {

    private final RegistrationService registrationService;
    public UserRegistration(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
    @PostMapping("/register")
    public String register(@RequestBody User user){
        return registrationService.register(user);
    }
}
