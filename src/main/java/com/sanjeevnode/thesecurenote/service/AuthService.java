package com.sanjeevnode.thesecurenote.service;

import com.sanjeevnode.thesecurenote.dto.auth.LoginRequest;
import com.sanjeevnode.thesecurenote.dto.auth.RegisterRequest;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;

public interface AuthService {
    CustomResponse register(RegisterRequest registerRequest);
    CustomResponse login(LoginRequest loginRequest);
}
