package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordResponseDto;

public interface UaaUsersService {
    ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);
}
