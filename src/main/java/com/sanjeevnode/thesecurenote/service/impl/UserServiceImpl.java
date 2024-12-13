package com.sanjeevnode.thesecurenote.service.impl;

import com.sanjeevnode.thesecurenote.dto.userDto.MasterPinRequest;
import com.sanjeevnode.thesecurenote.dto.userDto.UserDTO;
import com.sanjeevnode.thesecurenote.entity.User;
import com.sanjeevnode.thesecurenote.repository.UserRepository;
import com.sanjeevnode.thesecurenote.service.UserService;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomResponse getUser(String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with username " + username)
            );
            return new CustomResponse(HttpStatus.OK, "User found", UserDTO.fromEntity(user));
        } catch (Exception e) {
            return new CustomResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse updateEmailVerificationStatus(Long userId, Boolean status) {
        return null;
    }

    @Override
    public CustomResponse checkMasterPin(Long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with id " + userId)
            );
            HashMap<String, Boolean> response = new HashMap<>();
            if (user.getMasterPin() != null) {
                response.put("masterPin", true);
                return new CustomResponse(HttpStatus.OK, "Master Pin exists", response);
            } else {
                response.put("masterPin", false);
                return new CustomResponse(HttpStatus.OK, "Master Pin does not exist", response);
            }
        } catch (Exception e) {
            return new CustomResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse verifyMasterPin(Long userId, MasterPinRequest masterPinRequest) {
        try {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with id " + userId)
            );
            if (user.getMasterPin() == null) {
                return new CustomResponse(HttpStatus.BAD_REQUEST, "Master Pin not set", null);
            }
            if (passwordEncoder.matches(masterPinRequest.currentMasterPin(), user.getMasterPin())) {
                return new CustomResponse(HttpStatus.OK, "Master Pin verified", null);
            } else {
                return new CustomResponse(HttpStatus.BAD_REQUEST, "Invalid master pin", null);
            }
        } catch (Exception e) {
            return new CustomResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse updateMasterPin(Long userId, MasterPinRequest masterPinRequest) {
        try {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with id " + userId)
            );
            if (masterPinRequest.currentMasterPin() == null || masterPinRequest.newMasterPin() == null) {
                return new CustomResponse(HttpStatus.BAD_REQUEST, "Current and new master pin required", null);
            }
            if (user.getMasterPin() == null || passwordEncoder.matches(masterPinRequest.currentMasterPin(), user.getMasterPin())) {
                user.setMasterPin(passwordEncoder.encode(masterPinRequest.newMasterPin()));
                userRepository.save(user);
                return new CustomResponse(HttpStatus.OK, "Master Pin updated", null);
            } else {
                throw new Exception("Invalid current master pin.");
            }
        } catch (Exception e) {
            return new CustomResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }
}
