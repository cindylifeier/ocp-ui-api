package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
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

    public Object getAllGroups() {
        GroupWrapperDto wrapperGroup = oAuth2GroupRestClient.getAllGroups();

        return wrapperGroup.getResources().stream().filter(resource -> {
            return resource.getDisplayName().contains("ocp.role.") && !resource.getDisplayName().contains("ocpAdmin") && !resource.getDisplayName().contains("smartUser") && !resource.getDisplayName().contains("smartAdmin");
        }).collect(toList());
    }

    public Object getAllScopes() {
        GroupWrapperDto wrapperScope = oAuth2GroupRestClient.getAllGroups();

        return wrapperScope.getResources().stream().filter(resource -> {
            return resource.getDisplayName().contains("ocpUiApi") || resource.getDisplayName().contains("smartUser");
        }).collect(toList());
    }

    public UserWrapperDto getAllUsers() {
        UserWrapperDto wrapperUser = oAuth2GroupRestClient.getAllUsers();
        return wrapperUser;
    }

    public List<UserInfoDto> getAllUserInfos() {
        List<UserInfoDto> userInfoDtos = oAuth2GroupRestClient.getAllUserInfos();
        return userInfoDtos;
    }

    public Object getAllUsersByOrganization() {
        //TODO: Write business logic here..
        return getAllUserInfos();
    }
}
