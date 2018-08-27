package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.FHIRUaaUserDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.ManageUserDto;
import gov.samhsa.ocp.ocpuiapi.service.UaaUsersService;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerRoleDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordResponseDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ResetPasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.RoleToUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserDto;
import gov.samhsa.ocp.ocpuiapi.service.exception.BadRequestException;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class UaaUsersController {

    @Autowired
    private UaaUsersService uaaUsersService;

    @Autowired
    FisClient fisClient;

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
    public void createUser(@Valid @RequestBody UserDto userDto) {
        uaaUsersService.createUser(userDto);
    }


    @PutMapping("/users/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public void assignRoleToUser(@PathVariable("userId") String userId, @PathVariable("groupId") String groupId) {
        uaaUsersService.assignRoleToUser(RoleToUserDto.builder().groupId(groupId).userId(userId).build());
    }

    @GetMapping("/users")
    public List<ManageUserDto> getUsers(@RequestParam(value = "organizationId", required = false) String organizationId, @RequestParam(value = "resource") String resource, @RequestParam(value = "resourceId", required = false) String resourceId) {
        if (organizationId != null && resource != null)
            return uaaUsersService.getAllUsersByOrganizationId(organizationId, resource);
        if (resourceId != null && resource != null)
            return uaaUsersService.getUserByFhirResouce(resourceId, resource);
        throw new BadRequestException("Please provide valid criteria");
    }

    @GetMapping("/manage-users")
    public List<FHIRUaaUserDto> getPractitionerUsers(@RequestParam(value = "organizationId", required = false) String organizationId, @RequestParam(value = "resource") String resource) {
        List<PractitionerDto> practitioners = fisClient.searchPractitioners(null, null, organizationId, true, null, null, true).getElements();

        return practitioners.stream().map(fp -> {
            FHIRUaaUserDto fhirUaaUserDto = new FHIRUaaUserDto();
            fhirUaaUserDto.setLogicalId(fp.getLogicalId());
            fp.getName().stream().findAny().ifPresent(n -> {
                fhirUaaUserDto.setFamilyName(n.getLastName());
                fhirUaaUserDto.setGivenName(n.getFirstName());
            });
            fhirUaaUserDto.setAddresses(fp.getAddresses());
            fhirUaaUserDto.setIdentifiers(fp.getIdentifiers());
            fhirUaaUserDto.setActive(fp.isActive());
            fhirUaaUserDto.setTelecomDtos(fp.getTelecoms());
            List<PractitionerRoleDto> roles = fp.getPractitionerRoles().stream().map(f -> {
                if (getUsers(f.getOrganization().getReference().split("/")[1], resource, fp.getLogicalId()).isEmpty()) {
                    return f;
                } else {
                    f.setUaaRole(Optional.of(getUsers(f.getOrganization().getReference().split("/")[1], resource, fp.getLogicalId()).stream().findFirst().get().getDescription()));
                    return f;
                }
            }).collect(Collectors.toList());
            fhirUaaUserDto.setPractitionerRoles(roles);
            return fhirUaaUserDto;
        }).collect(Collectors.toList());
    }
}
