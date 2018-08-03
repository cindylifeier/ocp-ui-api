package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.service.UaaUsersService;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordResponseDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ResetPasswordRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class UaaUsersController {

    @Autowired
    private UaaUsersService uaaUsersService;

    @PutMapping("change-password")
    public ChangePasswordResponseDto changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        return uaaUsersService.changePassword(changePasswordRequestDto);
    }

    @PutMapping("users/{userId}/reset-password")
    public ChangePasswordResponseDto resetPassword(@PathVariable String userId, @Valid @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        return uaaUsersService.resetPassword(userId, resetPasswordRequestDto);
    }
}
