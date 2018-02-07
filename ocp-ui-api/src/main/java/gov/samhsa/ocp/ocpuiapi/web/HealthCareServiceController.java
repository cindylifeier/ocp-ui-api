package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.HealthCareServiceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ResourceType;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis")

public class HealthCareServiceController {
    @Autowired
    private FisClient fisClient;

    @GetMapping("/health-care-services")
    public PageDto<HealthCareServiceDto> getAllHealthCareServices(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                  @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                  @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Fetching health care services from FHIR Server...");
        PageDto<HealthCareServiceDto> fisClientResponse = fisClient.getAllHealthCareServices(statusList, searchKey, searchValue, pageNumber, pageSize);
        log.info("Got response from FHIR Server...");
        return fisClientResponse;
    }

    @GetMapping("/organizations/{organizationId}/health-care-services")
    public PageDto<HealthCareServiceDto> getAllHealthCareServicesByOrganization(@PathVariable String organizationId,
                                                                                @RequestParam(value = "assignedToLocationId", required = false) String assignedToLocationId,
                                                                                @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                                @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                                @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                                @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Fetching health care services from FHIR Server for the given OrganizationId: " + organizationId);
        try {
            PageDto<HealthCareServiceDto> fisClientResponse = fisClient.getAllHealthCareServicesByOrganization(organizationId, assignedToLocationId, statusList, searchKey, searchValue, pageNumber, pageSize);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no health care services were found in the configured FHIR server for the given OrganizationId", ResourceType.ORGANIZATION.name());
            return null;
        }
    }

    @GetMapping("/health-care-services/{healthCareServiceId}")
    public HealthCareServiceDto getHealthCareService(@PathVariable String healthCareServiceId) {
        log.info("Fetching locations from FHIR Server for the given healthCareServiceId: " + healthCareServiceId);
        try {
            HealthCareServiceDto fisClientResponse = fisClient.getHealthCareService(healthCareServiceId);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no location was found in the configured FHIR server for the given LocationId", ResourceType.HEALTHCARE_SERVICE.name());
            return null;
        }
    }


    @PutMapping("/health-care-services/{healthCareServiceId}/assign")
    @ResponseStatus(HttpStatus.OK)
    public void assignLocationToHealthCareService(@PathVariable String healthCareServiceId,
                                                  @RequestParam(value = "organizationId") String organizationId,
                                                  @RequestParam(value = "locationIdList") List<String> locationIdList) {
        log.info("About to assign locations to the health care service...");
        try {
            fisClient.assignLocationToHealthCareService(healthCareServiceId, organizationId, locationIdList);
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedAssigningLocToHealthCareService(fe, " the location(s) were not assigned to the health care service.");
        }
        log.info("Successfully assigned all locations to the health care service.");
    }

    @PostMapping("/organization/{organizationId}/health-care-service")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLocation(@PathVariable String organizationId,
                               @Valid @RequestBody HealthCareServiceDto healthCareServiceDto) {
        log.info("About to create a Healthcare Service");
        try {
            fisClient.createHealthCareService(organizationId, healthCareServiceDto);
            log.info("Successfully created the healthcare service");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, " that the health care service was not created", ResourceType.HEALTHCARE_SERVICE.name());
        }
    }


}
