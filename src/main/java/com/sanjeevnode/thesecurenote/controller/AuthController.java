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
//
//    @GetMapping("/test")
//    public ResponseEntity<CustomResponse> testCrypto(){
//        try{
//            String password = "user-password";
//            String saltString = "user@example.com"; // Derive salt from user-specific string
//
//            // Derive key
//            SecretKey key = CryptoUtils.deriveKey(password, saltString);
//
//            // Encrypt
//            String plainText = "This is a secret note.";
//            String encryptedText = CryptoUtils.encrypt(plainText, key);
//            System.out.println("Encrypted: " + encryptedText);
//
//            // Decrypt
//            String decryptedText = CryptoUtils.decrypt(encryptedText, key);
//            System.out.println("Decrypted: " + decryptedText);
//
//            Map<String,String> map= Map.of(
//                    "password",password,
//                    "salt-String",saltString,
//                    "plainText",plainText,
//                    "encryptedText",encryptedText,
//                    "decryptedText",decryptedText
//            );
//
//            return  ResponseEntity.status(HttpStatus.OK).body(
//                    CustomResponse.builder()
//                            .status(HttpStatus.OK)
//                            .message("Encryption Successful")
//                            .body(map)
//                            .build()
//            );
//        } catch (Exception e) {
//            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    CustomResponse.builder()
//                            .status(HttpStatus.BAD_REQUEST)
//                            .message("Encryption failed")
//                            .build()
//            );
//        }
//    }

}
