package com.example.backend.services;

import com.example.backend.repos.UserRepository;
import com.example.backend.model.Token.ConfirmationToken;
import com.example.backend.model.User.User;
import com.example.backend.model.User.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;

    }
    private final static String USER_NOT_FOUND_MSG = "Apologies! We couldn't find an account associated with the provided email address : %s";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository.existsByEmail(user.getEmail());
        if (userExists) {
            return "User already exists";
        }
        else {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            User userToSave = new User(
                    user.getUsername(),
                    encodedPassword,
                    user.getEmail(),
                    UserRole.USER,
                    false,
                    false
                    );
            userRepository.save(userToSave);
            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                userToSave

            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = "http://localhost:8080/api/registration/confirm?token=" + token;
            emailService.send(userToSave.getEmail(), userToSave.getUsername(), link);
            return "Success";

        }

    }

    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public void enableUser(String email) {userRepository.enableUser(email);}


    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            if (!user.isEnabled()) {
                throw new IllegalStateException("Oops! It seems you haven't activated your account yet");
            }
            return user;
        } else {
            throw new IllegalStateException("Oops! The password you entered is incorrect");
        }
    }

}
