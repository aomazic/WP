package com.example.backend.Service;

import com.example.backend.Repo.UserRepository;
import com.example.backend.Token.ConfirmationToken;
import com.example.backend.model.User.User;
import com.example.backend.model.User.UserRole;
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

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;

    }
    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository.existsByEmail(user.getEmail());
        if (userExists) {
            throw new IllegalStateException("email already taken");
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
            return token;

        }

    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

}