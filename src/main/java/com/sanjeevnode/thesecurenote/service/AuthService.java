package com.sanjeevnode.thesecurenote.service;

import com.sanjeevnode.thesecurenote.dto.authDto.LoginRequest;
import com.sanjeevnode.thesecurenote.dto.authDto.RegisterRequest;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;

public interface AuthService {
    CustomResponse register(RegisterRequest registerRequest);
    CustomResponse login(LoginRequest loginRequest);
}
