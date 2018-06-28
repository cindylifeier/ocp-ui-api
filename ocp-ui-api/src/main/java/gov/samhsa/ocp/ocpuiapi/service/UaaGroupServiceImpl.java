package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.RoleToUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupWrapperDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserWrapperDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.userinfo.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class UaaGroupServiceImpl implements UaaGroupService {

    @Autowired
    OAuth2GroupRestClient oAuth2GroupRestClient;

    @Override
    public Object getAllGroups() {
        GroupWrapperDto wrapperGroup = oAuth2GroupRestClient.getAllGroups();

        return wrapperGroup.getResources().stream().filter(resource -> {
            return resource.getDisplayName().contains("ocp.role.") && !resource.getDisplayName().contains("ocpAdmin") && !resource.getDisplayName().contains("smartUser") && !resource.getDisplayName().contains("smartAdmin");
        }).collect(toList());
    }

    @Override
    public Object getAllScopes() {
        GroupWrapperDto wrapperScope = oAuth2GroupRestClient.getAllGroups();

        return wrapperScope.getResources().stream().filter(resource -> {
            return resource.getDisplayName().contains("ocpUiApi") || resource.getDisplayName().contains("smartUser");
        }).collect(toList());
    }

    @Override
    public Object getAllUsersByOrganizationId(String organizationId) {
        return oAuth2GroupRestClient.getUsersByOrganizationId(organizationId);
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
}
