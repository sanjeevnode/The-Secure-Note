package com.sanjeevnode.thesecurenote.controller;

import com.sanjeevnode.thesecurenote.dto.user.MasterPinRequest;
import com.sanjeevnode.thesecurenote.service.UserService;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{userId}/checkMasterPin")
    public ResponseEntity<CustomResponse> checkMasterPin(@PathVariable Long userId) {
        CustomResponse response = userService.checkMasterPin(userId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{userId}/verifyMasterPin")
    public ResponseEntity<CustomResponse> verifyMasterPin(@PathVariable Long userId , @RequestBody MasterPinRequest masterPinRequest) {
        CustomResponse response = userService.verifyMasterPin(userId, masterPinRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("{userId}/updateMasterPin")
    public ResponseEntity<CustomResponse> updateMasterPin(@PathVariable Long userId , @RequestBody MasterPinRequest masterPinRequest) {
        CustomResponse response = userService.updateMasterPin(userId, masterPinRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
