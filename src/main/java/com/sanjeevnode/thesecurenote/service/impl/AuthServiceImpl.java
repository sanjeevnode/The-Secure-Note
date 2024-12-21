package com.sanjeevnode.thesecurenote.service.impl;

import com.sanjeevnode.thesecurenote.dto.auth.LoginRequest;
import com.sanjeevnode.thesecurenote.dto.auth.RegisterRequest;
import com.sanjeevnode.thesecurenote.entity.User;
import com.sanjeevnode.thesecurenote.repository.UserRepository;
import com.sanjeevnode.thesecurenote.service.AuthService;
import com.sanjeevnode.thesecurenote.service.jwt.JwtService;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import com.sanjeevnode.thesecurenote.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public CustomResponse register(RegisterRequest registerRequest) {
        try {
            if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
                return CustomResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("User already exists with username " + registerRequest.getUsername())
                        .build();
            }

            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            User savedUser = userRepository.save(user);

            String token = jwtService.generateToken(savedUser);

            return new CustomResponse(HttpStatus.OK, "User Created", token);
        } catch (Exception e) {
            return CustomResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public CustomResponse login(LoginRequest loginRequest) {
        try {
            User user = userRepository.findByUsername(loginRequest.username())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + loginRequest.username()));

            if (user.getFailedLoginAttempts() >= 3 && isAccountLocked(user)) {
                return CustomResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Account is temporarily locked, retry after " + Helper.milliSecondsToTime(user.getLockTime() - (System.currentTimeMillis() - user.getStartLockTime())))
                        .build();
            }

            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
                resetFailedLoginAttempts(user);
            } catch (AuthenticationException e) {
                handleFailedLoginAttempt(user);
                if (user.getFailedLoginAttempts() >= 3 && isAccountLocked(user)) {
                    return CustomResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Account is temporarily locked, retry after " + Helper.milliSecondsToTime(user.getLockTime() - (System.currentTimeMillis() - user.getStartLockTime())))
                            .build();
                }
                return CustomResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Invalid username or password, " + (3 - user.getFailedLoginAttempts()) + " retry remains.")
                        .build();
            }

            String token = jwtService.generateToken(user);
            return new CustomResponse(HttpStatus.OK, "Login Successful", token);
        } catch (Exception e) {
            return CustomResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }

    private boolean isAccountLocked(User user) {
        return System.currentTimeMillis() - user.getStartLockTime() < user.getLockTime();
    }

    private void resetFailedLoginAttempts(User user) {
        user.setFailedLoginAttempts(0);
        user.setLockTime(null);
        user.setStartLockTime(null);
        userRepository.save(user);
    }

    private void handleFailedLoginAttempt(User user) {
        int newFailedLoginAttempts = user.getFailedLoginAttempts() + 1;
        if (newFailedLoginAttempts >= 3) {
            user.setStartLockTime(System.currentTimeMillis());
            user.setLockTime(Helper.lockTimeInMilliSeconds(newFailedLoginAttempts));
        }
        user.setFailedLoginAttempts(newFailedLoginAttempts);
        userRepository.save(user);
    }
}
