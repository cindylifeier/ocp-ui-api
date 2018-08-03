package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordResponseDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ResetPasswordRequestDto;

public interface UaaUsersService {
    ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    ChangePasswordResponseDto resetPassword(String userId, ResetPasswordRequestDto resetPasswordRequestDto);
}
