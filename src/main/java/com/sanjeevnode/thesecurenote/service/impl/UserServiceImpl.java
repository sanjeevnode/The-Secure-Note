package com.sanjeevnode.thesecurenote.service.impl;

import com.sanjeevnode.thesecurenote.dto.userDto.UserDTO;
import com.sanjeevnode.thesecurenote.entity.User;
import com.sanjeevnode.thesecurenote.repository.UserRepository;
import com.sanjeevnode.thesecurenote.service.UserService;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public CustomResponse getUser(String username) {
        try{
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with username " + username)
            );
            return new CustomResponse(HttpStatus.OK, "User found", UserDTO.fromEntity(user));
        }
        catch(Exception e){
            return new CustomResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse updateEmailVerificationStatus(Long userId, Boolean status) {
        return null;
    }

    @Override
    public CustomResponse updateMasterPin(Long userId, String masterPin) {
        return null;
    }
}
