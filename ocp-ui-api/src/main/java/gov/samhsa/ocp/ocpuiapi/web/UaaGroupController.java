package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
import gov.samhsa.ocp.ocpuiapi.service.UaaGroupService;
import gov.samhsa.ocp.ocpuiapi.service.UserContextService;
import gov.samhsa.ocp.ocpuiapi.service.dto.UserContextDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @GetMapping("/userinfos")
    public Object getAllUserInfo(Principal principal) {
        return uaaGroupService.getAllUsersByOrganization();
    }

    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGroup(@Valid @RequestBody GroupRequestDto groupDto) {
        uaaGroupService.createGroup(groupDto);
    }


}
