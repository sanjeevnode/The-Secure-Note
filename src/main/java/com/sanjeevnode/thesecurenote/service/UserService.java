package com.sanjeevnode.thesecurenote.service;

import com.sanjeevnode.thesecurenote.utils.CustomResponse;

public interface UserService {
    CustomResponse getUser(String username);

    CustomResponse updateEmailVerificationStatus(Long userId, Boolean status);

    CustomResponse updateMasterPin(Long userId, String masterPin);
}
