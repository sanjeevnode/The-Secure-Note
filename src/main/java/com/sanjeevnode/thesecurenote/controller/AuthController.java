package com.sanjeevnode.thesecurenote.controller;

import com.sanjeevnode.thesecurenote.dto.auth.LoginRequest;
import com.sanjeevnode.thesecurenote.dto.auth.RegisterRequest;
import com.sanjeevnode.thesecurenote.service.AuthService;
import com.sanjeevnode.thesecurenote.utils.CryptoUtils;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Map;

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
