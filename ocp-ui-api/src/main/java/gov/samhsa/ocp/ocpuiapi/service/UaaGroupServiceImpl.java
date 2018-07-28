package gov.samhsa.ocp.ocpuiapi.service;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.EmailDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UaaNameDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UaaUserDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UaaUserInfoDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PatientDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.RoleToUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupMemberDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupWithScopesDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserResourceDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UaaGroupServiceImpl implements UaaGroupService {

    @Autowired
    OAuth2GroupRestClient oAuth2GroupRestClient;

    @Autowired
    FisClient fisClient;

    @Override
    public Object getAllGroups() {
        List<GroupDto> groups = oAuth2GroupRestClient.getAllGroups();

        Map<String, GroupWithScopesDto> map = new HashMap<>();

        for (GroupDto groupDto : groups) {
            GroupWithScopesDto groupWithScopesDto = map.get(groupDto.getId());
            if (map.get(groupDto.getId()) == null) {
                GroupWithScopesDto newGroupWithScopesDto = new GroupWithScopesDto();

                newGroupWithScopesDto.setId(groupDto.getId());
                newGroupWithScopesDto.setDisplayName(groupDto.getDisplayName());
                newGroupWithScopesDto.setDescription(groupDto.getDescription());
                List<String> scopes = new ArrayList<>();
                scopes.add(groupDto.getScopeId());
                newGroupWithScopesDto.setScopes(scopes);

                map.put(groupDto.getId(), newGroupWithScopesDto);
            } else {
                List<String> scopes = groupWithScopesDto.getScopes();

                scopes.add(groupDto.getScopeId());
            }
        }

        return map.values();
    }

    @Override
    public Object getAllScopes() {
        return oAuth2GroupRestClient.getAllScopes();
    }

    @Override
    public Object getAllUsersByOrganizationId(String organizationId, String resource) {
        return oAuth2GroupRestClient.getUsersByOrganizationId(organizationId, resource);
    }

    @Override
    public void createGroup(GroupRequestDto groupRequestDto) {
        oAuth2GroupRestClient.createGroup(groupRequestDto);
    }

    @Override
    public void updateGroup(String groupId, GroupRequestDto groupDto) {
        oAuth2GroupRestClient.updateGroup(groupId, groupDto);
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
            ExceptionUtil.handleFeignException(fe, "Failed to create user for FHIR resource: " + userDto.getResource() + "/" + userDto.getResourceId() + "with username" + userDto.getUsername());
        }
    }
}
