package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
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
    OAuth2GroupRestClient oAuth2GroupRestClient;

    @GetMapping("/groups")
    public Object getUserResource(Principal principal) {
        return oAuth2GroupRestClient.getAllGroups();
    }

    @GetMapping("/users")
    public Object getAllUsersByOrganization(Principal principal) {
        return oAuth2GroupRestClient.getAllUsersByOrganization();
    }


}
