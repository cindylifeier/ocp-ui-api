package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.JwtTokenExtractor;
import gov.samhsa.ocp.ocpuiapi.service.dto.JwtTokenKey;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.UserContextDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@Slf4j
public class UserContextController {
    @Autowired
    FisClient fisClient;

    @Autowired
    JwtTokenExtractor jwtTokenExtractor;

    @GetMapping("/user-context")
    public UserContextDto getUserResource(Principal principal) {
        Map extAttr = (Map) jwtTokenExtractor.getValueByKey(JwtTokenKey.EXT_ATTR);
        Object resource = null;
        if(extAttr.get("resource").toString().equalsIgnoreCase("Practitioner")){
            resource = fisClient.getPractitioner(extAttr.get("id").toString());
        }
        if(extAttr.get("resource").toString().equalsIgnoreCase("Patient")){
            resource = fisClient.getPatientById(extAttr.get("id").toString());
        }

        OrganizationDto organization = fisClient.getOrganization(jwtTokenExtractor.getOrganizationId());

        return UserContextDto.builder().organization(organization).resource(resource).build();

    }

}
