package com.sanjeevnode.thesecurenote.controller;

import com.sanjeevnode.thesecurenote.service.UserService;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{username}")
    public ResponseEntity<CustomResponse> getUser(@PathVariable String username) {
       CustomResponse response = userService.getUser(username);
         return ResponseEntity.status(response.getStatus()).body(response);
    }
}
