package com.sanjeevnode.thesecurenote.controller;

import com.sanjeevnode.thesecurenote.dto.authDto.LoginRequest;
import com.sanjeevnode.thesecurenote.dto.authDto.RegisterRequest;
import com.sanjeevnode.thesecurenote.service.AuthService;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CustomResponse> register(@RequestBody RegisterRequest registerRequest){
        CustomResponse response = authService.register(registerRequest);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody LoginRequest loginRequest){
        CustomResponse response = authService.login(loginRequest);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

}
