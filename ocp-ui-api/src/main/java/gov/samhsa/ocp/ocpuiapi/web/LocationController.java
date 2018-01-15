package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.LocationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ResourceType;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis")
public class LocationController {

    @Autowired
    private FisClient fisClient;

    @GetMapping("/locations")
    public PageDto<LocationDto> getAllLocations(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                                @RequestParam(value = "searchKey", required = false) String searchKey,
                                                @RequestParam(value = "searchValue", required = false) String searchValue,
                                                @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Fetching locations from FHIR Server...");
        try {
            PageDto<LocationDto> fisClientResponse = fisClient.getAllLocations(statusList, searchKey, searchValue, pageNumber, pageSize);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no locations were found in the configured FHIR server", ResourceType.LOCATION.name());
            return null;
        }

    }

    @GetMapping("/organizations/{organizationId}/locations")
    public PageDto<LocationDto> getLocationsByOrganization(@PathVariable String organizationId,
                                                           @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                           @RequestParam(value = "searchKey", required = false) String searchKey,
                                                           @RequestParam(value = "searchValue", required = false) String searchValue,
                                                           @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Fetching locations from FHIR Server for the given OrganizationId: " + organizationId);
        try {
            PageDto<LocationDto> fisClientResponse = fisClient.getLocationsByOrganization(organizationId, statusList, searchKey, searchValue, pageNumber, pageSize);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no locations were found in the configured FHIR server for the given OrganizationId", ResourceType.LOCATION.name());
            return null;
        }
    }

    @GetMapping("/locations/{locationId}")
    public LocationDto getLocation(@PathVariable String locationId) {
        log.info("Fetching locations from FHIR Server for the given LocationId: " + locationId);
        try {
            LocationDto fisClientResponse = fisClient.getLocation(locationId);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no location was found in the configured FHIR server for the given LocationId", ResourceType.LOCATION.name());
            return null;
        }
    }

    @GetMapping("/locations/{locationId}/childLocation")
    public LocationDto getChildLocation(@PathVariable String locationId) {
        log.info("Fetching child location from FHIR Server for the given LocationId: " + locationId);
        try {
            LocationDto fisClientResponse = fisClient.getChildLocation(locationId);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no child location was found in the configured FHIR server for the given LocationId", ResourceType.LOCATION.name());
            return null;
        }
    }


}
