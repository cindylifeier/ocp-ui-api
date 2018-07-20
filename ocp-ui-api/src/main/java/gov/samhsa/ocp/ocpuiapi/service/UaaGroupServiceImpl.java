package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.EmailDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UaaNameDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.UaaUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PatientDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.RoleToUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupWithScopesDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

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
        UaaUserDto uaaUserDto=new UaaUserDto();
        uaaUserDto.setUserName(userDto.getUserName());
        uaaUserDto.setPassword(userDto.getPassword());

        if(userDto.getResource().equalsIgnoreCase("Practitioner")){
            PractitionerDto practitionerDto = fisClient.getPractitioner(userDto.getResourceId());
            UaaNameDto uaaNameDto=new UaaNameDto();
            uaaNameDto.setGivenName(practitionerDto.getName().stream().findFirst().get().getFirstName());
            uaaNameDto.setFamilyName(practitionerDto.getName().stream().findFirst().get().getLastName());
            uaaUserDto.setName(uaaNameDto);
            if(!practitionerDto.getTelecoms().isEmpty() && practitionerDto.getTelecoms()!=null){
                practitionerDto.getTelecoms().stream().filter(pr->pr.getSystem().equals("email")).findAny().ifPresent(email->{
                    EmailDto emailDto=new EmailDto();
                    emailDto.setValue(email.getValue().get());
                    emailDto.setPrimary(true);
                    uaaUserDto.setEmails(Arrays.asList(emailDto));
                });
            }
            uaaUserDto.setActive(true);
            uaaUserDto.setVerified(true);
            uaaUserDto.setOrigin("");
            oAuth2GroupRestClient.createUser(uaaUserDto);
        }else {
            PatientDto patientDto= (PatientDto) fisClient.getPatientById(userDto.getResourceId());
            UaaNameDto uaaNameDto=new UaaNameDto();
            uaaNameDto.setGivenName(patientDto.getName().stream().findFirst().get().getFirstName());
            uaaNameDto.setFamilyName(patientDto.getName().stream().findFirst().get().getLastName());
            uaaUserDto.setName(uaaNameDto);
            if(!patientDto.getTelecoms().isEmpty() && patientDto.getTelecoms()!=null){
                patientDto.getTelecoms().stream().filter(pr->pr.getSystem().equals("email")).findAny().ifPresent(email->{
                    EmailDto emailDto=new EmailDto();
                    emailDto.setValue(email.getValue().get());
                    emailDto.setPrimary(true);
                    uaaUserDto.setEmails(Arrays.asList(emailDto));
                });
            }

            oAuth2GroupRestClient.createUser(uaaUserDto);
        }

    }
}
