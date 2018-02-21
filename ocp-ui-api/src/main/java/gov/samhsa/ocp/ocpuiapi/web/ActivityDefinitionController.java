package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.ActivityDefinitionDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ResourceType;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ocp-fis")
@Slf4j
public class ActivityDefinitionController {
    @Autowired
    private FisClient fisClient;

    @GetMapping("/organizations/{organizationId}/activity-definitions")
    public Object getAllActivityDefinitionsByOrganization(@PathVariable String organizationId,
                                                                                @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                                @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                                @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Fetching activity definitions from FHIR Server for the given OrganizationId: " + organizationId);
        try {
            Object fisClientResponse = fisClient.getAllActivityDefinitionsByOrganization(organizationId, searchKey, searchValue, pageNumber, pageSize);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no activity definitions were found in the configured FHIR server for the given OrganizationId", ResourceType.ORGANIZATION.name());
            return null;
        }
    }


    @PostMapping("/organization/{organizationId}/activity-definitions")
    @ResponseStatus(HttpStatus.CREATED)
    public void createActivityDefinition(@PathVariable String organizationId,
                               @Valid @RequestBody ActivityDefinitionDto activityDefinitionDto) {
        log.info("About to create a activity definition");
        try {
            fisClient.createActivityDefinition(organizationId, activityDefinitionDto);
            log.info("Successfully created a activity definition");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, " that the activity definition was not created", ResourceType.ACTIVITY_DEFINITION.name());
        }
    }



}
