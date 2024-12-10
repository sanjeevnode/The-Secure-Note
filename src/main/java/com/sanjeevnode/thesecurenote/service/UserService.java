package com.sanjeevnode.thesecurenote.service;

import com.sanjeevnode.thesecurenote.dto.userDto.MasterPinRequest;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;

public interface UserService {
    CustomResponse getUser(String username);

    CustomResponse updateEmailVerificationStatus(Long userId, Boolean status);

    CustomResponse checkMasterPin(Long userId);

    CustomResponse verifyMasterPin(Long userId, MasterPinRequest masterPinRequest);

    CustomResponse updateMasterPin(Long userId, MasterPinRequest masterPinRequest);
}
