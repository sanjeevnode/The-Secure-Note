package com.sanjeevnode.thesecurenote.service.impl;

import com.sanjeevnode.thesecurenote.dto.authDto.LoginRequest;
import com.sanjeevnode.thesecurenote.dto.authDto.RegisterRequest;
import com.sanjeevnode.thesecurenote.entity.User;
import com.sanjeevnode.thesecurenote.repository.UserRepository;
import com.sanjeevnode.thesecurenote.service.AuthService;
import com.sanjeevnode.thesecurenote.service.jwt.JwtService;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final  PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public CustomResponse register(RegisterRequest registerRequest) {
        try {
            if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
                return new CustomResponse(HttpStatus.BAD_REQUEST, "User already exists with username "+ registerRequest.getUsername(), null);
            }

            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            User savedUser = userRepository.save(user);

            String token = jwtService.generateToken(savedUser);

            return new CustomResponse(HttpStatus.OK, "User Created", token);
        } catch (Exception e) {
            return new CustomResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse login(LoginRequest loginRequest) {
        try {
            User user = userRepository.findByUsername(loginRequest.username())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found of username " + loginRequest.username()));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );

            String token = jwtService.generateToken(user);

            return new CustomResponse(HttpStatus.OK, "Login Successful", token);
        } catch (Exception e) {
            return new CustomResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }
}
