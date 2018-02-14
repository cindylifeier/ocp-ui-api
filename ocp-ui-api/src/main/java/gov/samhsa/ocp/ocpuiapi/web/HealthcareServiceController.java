package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.HealthcareServiceDto;
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

public class HealthcareServiceController {
    @Autowired
    private FisClient fisClient;

    @GetMapping("/healthcare-services")
    public PageDto<HealthcareServiceDto> getAllHealthcareServices(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                  @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                  @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Fetching healthcare services from FHIR Server...");
        PageDto<HealthcareServiceDto> fisClientResponse = fisClient.getAllHealthcareServices(statusList, searchKey, searchValue, pageNumber, pageSize);
        log.info("Got response from FHIR Server...");
        return fisClientResponse;
    }

    @GetMapping("/organizations/{organizationId}/healthcare-services")
    public PageDto<HealthcareServiceDto> getAllHealthcareServicesByOrganization(@PathVariable String organizationId,
                                                                                @RequestParam(value = "assignedToLocationId", required = false) String assignedToLocationId,
                                                                                @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                                @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                                @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                                @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Fetching healthcare services from FHIR Server for the given OrganizationId: " + organizationId);
        try {
            PageDto<HealthcareServiceDto> fisClientResponse = fisClient.getAllHealthcareServicesByOrganization(organizationId, assignedToLocationId, statusList, searchKey, searchValue, pageNumber, pageSize);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no healthcare services were found in the configured FHIR server for the given OrganizationId", ResourceType.ORGANIZATION.name());
            return null;
        }
    }

    @GetMapping("/organizations/{organizationId}/locations/{locationId}/healthcare-services")
    public PageDto<HealthcareServiceDto> getAllHealthcareServicesByLocation(@PathVariable String organizationId,
                                                                            @PathVariable String locationId,
                                                                            @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                            @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                            @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Fetching healthcare services from FHIR Server for the given locationId: " + locationId);
        try {
            PageDto<HealthcareServiceDto> fisClientResponse = fisClient.getAllHealthcareServicesByLocation(organizationId, locationId, statusList, searchKey, searchValue, pageNumber, pageSize);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No healthcare services were found in the configured FHIR server for the given LocationId", ResourceType.HEALTHCARE_SERVICE.name());
            return null;
        }
    }

    @GetMapping("/healthcare-services/{healthcareServiceId}")
    public HealthcareServiceDto getHealthcareService(@PathVariable String healthcareServiceId) {
        log.info("Fetching locations from FHIR Server for the given healthcareServiceId: " + healthcareServiceId);
        try {
            HealthcareServiceDto fisClientResponse = fisClient.getHealthcareService(healthcareServiceId);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no location was found in the configured FHIR server for the given LocationId", ResourceType.HEALTHCARE_SERVICE.name());
            return null;
        }
    }


    @PutMapping("/healthcare-services/{healthcareServiceId}/assign")
    @ResponseStatus(HttpStatus.OK)
    public void assignLocationToHealthcareService(@PathVariable String healthcareServiceId,
                                                  @RequestParam(value = "organizationId") String organizationId,
                                                  @RequestParam(value = "locationIdList") List<String> locationIdList) {
        log.info("About to assign locations to the healthcare service...");
        try {
            fisClient.assignLocationToHealthcareService(healthcareServiceId, organizationId, locationIdList);
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedAssigningLocToHealthcareService(fe, " the location(s) were not assigned to the healthcare service.");
        }
        log.info("Successfully assigned all locations to the healthcare service.");
    }

    @PutMapping("/healthcare-services/{healthcareServiceId}/unassign")
    @ResponseStatus(HttpStatus.OK)
    public void unassignLocationToHealthcareService(@PathVariable String healthcareServiceId,
                                                  @RequestParam(value = "organizationId") String organizationId,
                                                  @RequestParam(value = "locationIdList") List<String> locationIdList) {
        log.info("About to unassign locations from the healthcare service...");
        try {
            fisClient.unassignLocationToHealthcareService(healthcareServiceId, organizationId, locationIdList);
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceUpdate(fe, " the location(s) were not unassigned from the healthcare service.", ResourceType.HEALTHCARE_SERVICE.name());
        }
        log.info("Successfully unassigned all locations from the healthcare service.");
    }

    @PostMapping("/organization/{organizationId}/healthcare-service")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHealthcareService(@PathVariable String organizationId,
                               @Valid @RequestBody HealthcareServiceDto healthcareServiceDto) {
        log.info("About to create a Healthcare Service");
        try {
            fisClient.createHealthcareService(organizationId, healthcareServiceDto);
            log.info("Successfully created the healthcare service");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, " that the healthcare service was not created", ResourceType.HEALTHCARE_SERVICE.name());
        }
    }

    @PutMapping("/organization/{organizationId}/healthcare-service/{healthcareServiceId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateHealthcareService(@PathVariable String organizationId,
                               @PathVariable String healthcareServiceId,
                               @Valid @RequestBody HealthcareServiceDto healthcareServiceDto) {
        log.info("About to update the Healthcare Service");
        try {
            fisClient.updateHealthcareService(organizationId, healthcareServiceId, healthcareServiceDto);
            log.info("Successfully updated the healthcare service");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceUpdate(fe, " that the healthcare service was not updated", ResourceType.HEALTHCARE_SERVICE.name());
        }
    }


}
