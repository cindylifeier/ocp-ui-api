package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
import gov.samhsa.ocp.ocpuiapi.service.UaaGroupService;
import gov.samhsa.ocp.ocpuiapi.service.UserContextService;
import gov.samhsa.ocp.ocpuiapi.service.dto.UserContextDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class UaaGroupController {

    @Autowired
    UaaGroupService uaaGroupService;

    @GetMapping("/groups")
    public Object getAllGroups(Principal principal) {
        return uaaGroupService.getAllGroups();
    }

    @GetMapping("/scopes")
    public Object getAllScopes(Principal principal) {
        return uaaGroupService.getAllScopes();
    }

    /*@GetMapping("/users")
    public Object getAllUsersByOrganization(Principal principal) {
        return oAuth2GroupRestClient.getAllUsersByOrganization();
    }*/


}
