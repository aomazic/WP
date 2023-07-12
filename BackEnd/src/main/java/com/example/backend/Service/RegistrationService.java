package com.example.backend.Service;

import com.example.backend.model.Token.ConfirmationToken;
import com.example.backend.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    private final EmailService emailService;
    @Autowired
    public RegistrationService(UserService userService, ConfirmationTokenService confirmationTokenService,  EmailService emailService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    public String register(User user) {
        return userService.signUpUser(user);
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            return "email already confirmed";
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            return "expired";
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    public String resendEmail(String email){
        User user = userService.getUserByEmail(email);
        String token = confirmationTokenService.getToken(user);
        String link = "http://localhost:8080/api/registration/confirm?token=" + token;
        emailService.send(user.getEmail(), user.getUsername(), link);
        return "Email sent";
    }

    public User login(String email, String password){
        return userService.loginUser(email, password);
    }
}
