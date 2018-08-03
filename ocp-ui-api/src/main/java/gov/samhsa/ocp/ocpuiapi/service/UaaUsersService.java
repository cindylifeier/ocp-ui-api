package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordResponseDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ResetPasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.RoleToUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserDto;

public interface UaaUsersService {
    ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    ChangePasswordResponseDto resetPassword(String userId, ResetPasswordRequestDto resetPasswordRequestDto);

    public void assignRoleToUser(RoleToUserDto roleToUserDto);

    public void createUser(UserDto userDto);

    public Object getAllUsersByOrganizationId(String organizationId, String resource);
}
