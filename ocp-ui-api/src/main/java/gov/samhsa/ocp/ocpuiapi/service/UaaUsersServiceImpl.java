package gov.samhsa.ocp.ocpuiapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2RestClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.UaaUserTokenRestClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.EmailDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.ManageUserDto;
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
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.Info;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserResourceDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class UaaUsersServiceImpl implements UaaUsersService {

    private static final String OCP_ROLE_ORGANIZATION_ADMINISTRATOR = "ocp.role.organizationAdministrator";
    private static final String OCP_ROLE_OCP_ADMIN = "ocp.role.ocpAdmin";
    private static final String PRACTITIONER = "Practitioner";
    private static final String PATIENT = "Patient";
    private final JwtTokenExtractor jwtTokenExtractor;
    private final UaaUserTokenRestClient uaaUserTokenRestClient;
    private final OAuth2RestClient oAuth2GroupRestClient;
    private final FisClient fisClient;

    @Autowired
    public UaaUsersServiceImpl(JwtTokenExtractor jwtTokenExtractor, UaaUserTokenRestClient uaaUserTokenRestClient, OAuth2RestClient oAuth2GroupRestClient, FisClient fisClient) {
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.uaaUserTokenRestClient = uaaUserTokenRestClient;
        this.oAuth2GroupRestClient = oAuth2GroupRestClient;
        this.fisClient = fisClient;
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
    public List<ManageUserDto> getAllUsersByOrganizationId(String organizationId, String resource) {
        PageDto<PractitionerDto> practitioners = fisClient.searchPractitioners(null, null, organizationId, true, null, null, true);
        List<PractitionerDto> fhirPractitioners = practitioners.getElements();
        List<ManageUserDto> uaaPractitioners = oAuth2GroupRestClient.getUsers(organizationId, resource, null);

        Map<String, ManageUserDto> map = new HashMap<>();

        for(ManageUserDto uaaUser : uaaPractitioners) {
            String uaaId = this.convertUaaUserToId(uaaUser);
            map.put(uaaId, uaaUser);
        }

      List<ManageUserDto> mappedUaaUsers = new ArrayList<>();
        for (PractitionerDto fhirUser : fhirPractitioners) {
            ManageUserDto uaaUser = map.get(fhirUser.getLogicalId());

            //some users do not have corresponding account in UAA
            if (uaaUser == null) {
                //just create empty ones
                uaaUser = new ManageUserDto();
                uaaUser.setId(new Random().nextInt(100000) + "");

                if(fhirUser.getName().get(0) != null) {
                    uaaUser.setGivenName(fhirUser.getName().get(0).getFirstName());
                }

                if(fhirUser.getName().get(0) != null) {
                    uaaUser.setFamilyName(fhirUser.getName().get(0).getLastName());
                }

                uaaUser.setGroupId("NA");
                uaaUser.setDisplayName("NA");
                uaaUser.setDescription("NA");
                uaaUser.setInfo("NA");
            }

            Optional<PractitionerRoleDto> role = fhirUser.getPractitionerRoles().stream().findFirst();

            if (role.isPresent()) {
                uaaUser.setRole(role.get().getCode());
            }

            mappedUaaUsers.add(uaaUser);
        }

        return mappedUaaUsers;
    }

    @Override
    public Object getUserByFhirResouce(String resourceId, String resource) {
        return oAuth2GroupRestClient.getUsers(null, resource, resourceId);
    }

    private String convertUaaUserToId(ManageUserDto user) {
        try {
            String info = user.getInfo();
            ObjectMapper mapper = new ObjectMapper();

            Info infoObj = mapper.readValue(info, Info.class);
            return infoObj.getUserAttributes().getId().stream().findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteUser(String userId) {
        oAuth2GroupRestClient.deleteUser(userId);
    }
}
