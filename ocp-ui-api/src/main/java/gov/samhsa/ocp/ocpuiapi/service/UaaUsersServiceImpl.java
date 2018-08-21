package gov.samhsa.ocp.ocpuiapi.service;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.UaaUserTokenRestClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.EmailDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UaaNameDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UaaUserDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UaaUserInfoDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.JwtTokenKey;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PatientDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerRoleDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordResponseDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ResetPasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.RoleToUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupMemberDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserResourceDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@Service
public class UaaUsersServiceImpl implements UaaUsersService {

    private static final String OCP_ROLE_ORGANIZATION_ADMINISTRATOR = "ocp.role.organizationAdministrator";
    private static final String OCP_ROLE_OCP_ADMIN = "ocp.role.ocpAdmin";
    private final JwtTokenExtractor jwtTokenExtractor;
    private final UaaUserTokenRestClient uaaUserTokenRestClient;
    private final OAuth2GroupRestClient oAuth2GroupRestClient;


    @Autowired
    FisClient fisClient;

    @Autowired
    public UaaUsersServiceImpl(JwtTokenExtractor jwtTokenExtractor, UaaUserTokenRestClient uaaUserTokenRestClient, OAuth2GroupRestClient oAuth2GroupRestClient) {
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.uaaUserTokenRestClient = uaaUserTokenRestClient;
        this.oAuth2GroupRestClient = oAuth2GroupRestClient;
    }

    @Override
    public ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        String userId = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID).toString();
        Assert.hasText(userId, "user_id must have text");
        ChangePasswordResponseDto changePasswordResponseDto = null;
        try {
            changePasswordResponseDto = uaaUserTokenRestClient.changePassword(userId, changePasswordRequestDto);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionFailToLogin(fe, "User change password failure with userId: ".concat(userId));
        }
        return changePasswordResponseDto;
    }

    @Override
    public ChangePasswordResponseDto resetPassword(String userId, ResetPasswordRequestDto resetPasswordRequestDto) {
        List<String> scopes = (List<String>) jwtTokenExtractor.getValueByKey(JwtTokenKey.SCOPE);
        boolean isOrgOrOcpAdmin = scopes.contains(OCP_ROLE_ORGANIZATION_ADMINISTRATOR) || scopes.contains(OCP_ROLE_OCP_ADMIN);
        Assert.hasText(userId, "user_id must have text");
        Assert.isTrue(isOrgOrOcpAdmin, "This user does not allow to do resetting password.");
        ChangePasswordResponseDto changePasswordResponseDto = null;
        try {
            changePasswordResponseDto = oAuth2GroupRestClient.resetPassword(userId, resetPasswordRequestDto);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionFailToLogin(fe, "Reset User password failure for userId: ".concat(userId));
        }
        return changePasswordResponseDto;
    }

    @Override
    public void assignRoleToUser(RoleToUserDto roleToUserDto) {
        oAuth2GroupRestClient.assignRoleToUser(roleToUserDto);
    }

    @Override
    public void createUser(UserDto userDto) {
        try {
            //Create user in UAA
            UaaUserDto uaaUserDto = new UaaUserDto();
            uaaUserDto.setUserName(userDto.getUsername());
            uaaUserDto.setPassword(userDto.getPassword());
            uaaUserDto.setActive(true);
            uaaUserDto.setVerified(true);

            if (userDto.getResource().equalsIgnoreCase("Practitioner")) {
                PractitionerDto practitionerDto = fisClient.getPractitioner(userDto.getResourceId());
                UaaNameDto uaaNameDto = new UaaNameDto();
                uaaNameDto.setGivenName(practitionerDto.getName().stream().findFirst().get().getFirstName());
                uaaNameDto.setFamilyName(practitionerDto.getName().stream().findFirst().get().getLastName());
                uaaUserDto.setName(uaaNameDto);
                if (!practitionerDto.getTelecoms().isEmpty() && practitionerDto.getTelecoms() != null) {
                    practitionerDto.getTelecoms().stream().filter(pr -> pr.getSystem().get().equalsIgnoreCase("email")).findAny().ifPresent(email -> {
                        EmailDto emailDto = new EmailDto();
                        emailDto.setValue(email.getValue().get());
                        uaaUserDto.setEmails(Arrays.asList(emailDto));
                    });
                }
            } else {
                PatientDto patientDto = fisClient.getPatientById(userDto.getResourceId());
                UaaNameDto uaaNameDto = new UaaNameDto();
                uaaNameDto.setGivenName(patientDto.getName().stream().findFirst().get().getFirstName());
                uaaNameDto.setFamilyName(patientDto.getName().stream().findFirst().get().getLastName());
                uaaUserDto.setName(uaaNameDto);
                if (!patientDto.getTelecoms().isEmpty() && patientDto.getTelecoms() != null) {
                    patientDto.getTelecoms().stream().filter(pr -> pr.getSystem().get().equalsIgnoreCase("email")).findAny().ifPresent(email -> {
                        EmailDto emailDto = new EmailDto();
                        emailDto.setValue(email.getValue().get());
                        uaaUserDto.setEmails(Arrays.asList(emailDto));
                    });
                }
            }
            UserResourceDto userResourceDto = oAuth2GroupRestClient.createUser(uaaUserDto);

            //Create userinfo in UAA
            UaaUserInfoDto uaaUserInfoDto = new UaaUserInfoDto();
            uaaUserInfoDto.setUser_id(Arrays.asList(userResourceDto.getId()));
            uaaUserInfoDto.setResource(Arrays.asList(userDto.getResource()));
            uaaUserInfoDto.setId(Arrays.asList(userDto.getResourceId()));
            uaaUserInfoDto.setOrgId((Arrays.asList(userDto.getRoles().get(0).getOrgId())));
            oAuth2GroupRestClient.createUserInfo(uaaUserInfoDto);

            //Assign user to role group
            oAuth2GroupRestClient.addGroupMember(userDto.getRoles().get(0).getRole(), GroupMemberDto.builder().origin("uaa").type("USER").value(userResourceDto.getId()).build());
        } catch (
                FeignException fe) {
            ExceptionUtil.handleUaaException(fe, "Failed to create user for FHIR resource: " + userDto.getResource() + "/" + userDto.getResourceId() + "with username" + userDto.getUsername());
        }
    }

    @Override
    public List<gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UserDto> getAllUsersByOrganizationId(String organizationId, String resource) {
        PageDto<PractitionerDto> practitioners = fisClient.searchPractitioners(null, null, organizationId, true, null, null, true);
        List<PractitionerDto> fhirPractitioners = practitioners.getElements();
        List<gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UserDto> uaaPractitioners = oAuth2GroupRestClient.getUsersByOrganizationId(organizationId, resource);

        for (gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UserDto dto : uaaPractitioners) {

            for (PractitionerDto practitionerDto : fhirPractitioners) {
                if (dto.getInfo().contains(practitionerDto.getLogicalId())) {
                    List<PractitionerRoleDto> roles = practitionerDto.getPractitionerRoles();

                    roles.stream().findFirst().ifPresent(role -> {
                        dto.setRole(role.getCode());
                    });
                }
            }
        }

        return uaaPractitioners;
    }
}
