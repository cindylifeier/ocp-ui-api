package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupWrapperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return resource.getDisplayName().contains("ocpUiApi");
        }).collect(toList());
    }
}
