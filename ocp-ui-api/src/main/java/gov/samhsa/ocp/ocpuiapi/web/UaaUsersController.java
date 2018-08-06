package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.service.UaaUsersService;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordResponseDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ResetPasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.RoleToUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserDto userDto){
        uaaUsersService.createUser(userDto);
    }


    @PutMapping("/users/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public void assignRoleToUser(@PathVariable("userId") String userId, @PathVariable("groupId") String groupId ) {
        uaaUsersService.assignRoleToUser(RoleToUserDto.builder().groupId(groupId).userId(userId).build());
    }

    @GetMapping("/users")
    public Object getUsersByOrganizationId(@RequestParam(value="organizationId", required = true) String organizationId, @RequestParam(value="resource") String resource) {
        return uaaUsersService.getAllUsersByOrganizationId(organizationId, resource);
    }
}
