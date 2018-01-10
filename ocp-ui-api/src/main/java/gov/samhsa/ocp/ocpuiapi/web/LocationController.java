package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.exception.client.FisClientInterfaceException;
import gov.samhsa.ocp.ocpuiapi.service.exception.location.LocationNotFoundException;
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
    public Object getAllLocations(@RequestParam(value = "status", required = false)List<String> status,
                                  @RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "size", required = false) Integer size) {
        log.info("Fetching locations from FHIR Server...");
        try {
            Object fisClientResponse = fisClient.getAllLocations(status, page, size);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            handleFeignExceptionRelatedToLocationSearch(fe, "no locations were found in the configured FHIR server");
            return null;
        }

    }

    @GetMapping("/organizations/{organizationId}/locations")
    public Object getLocationsByOrganization(@PathVariable String organizationId,
                                             @RequestParam(value = "status", required = false)List<String> status,
                                             @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "size", required = false) Integer size) {
        log.info("Fetching locations from FHIR Server for the given OrganizationId: "+ organizationId);
        try {
            Object fisClientResponse = fisClient.getLocationsByOrganization(organizationId, status, page, size);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            handleFeignExceptionRelatedToLocationSearch(fe, "no locations were found in the configured FHIR server for the given OrganizationId");
            return null;
        }
    }

    @GetMapping("/locations/{locationId}")
    public Object getLocation(@PathVariable String locationId) {
        log.info("Fetching locations from FHIR Server for the given OrganizationId: "+ locationId);
        try {
            Object fisClientResponse = fisClient.getLocation(locationId);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            handleFeignExceptionRelatedToLocationSearch(fe, "no location was found in the configured FHIR server for the given LocationId");
            return null;
        }
    }

    @GetMapping("/locations/{locationId}/childLocation")
    public Object getChildLocation(@PathVariable String locationId) {
        log.info("Fetching child location from FHIR Server for the given OrganizationId: "+ locationId);
        try {
            Object fisClientResponse = fisClient.getChildLocation(locationId);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            handleFeignExceptionRelatedToLocationSearch(fe, "no child location was found in the configured FHIR server for the given LocationId");
            return null;
        }
    }

    private String getErrorMessageFromFeignException(FeignException fe) {
        String detailMessage = fe.getMessage();
        String array[] = detailMessage.split("message");
        if (array.length > 1) {
            return array[1].substring(array[1].indexOf("\":\"") + 3, array[1].indexOf("\",\""));
        } else return detailMessage;
    }

    private void handleFeignExceptionRelatedToLocationSearch(FeignException fe, String logErrorMessage){
        int causedByStatus = fe.status();
        switch (causedByStatus) {
            case 404:
                String errorMessage = getErrorMessageFromFeignException(fe);
                String logErrorMessageWithCode = "Fis client returned a 404 - NOT FOUND status, indicating " + logErrorMessage;
                log.error(logErrorMessageWithCode, fe);
                throw new LocationNotFoundException(errorMessage);
            default:
                log.error("Fis client returned an unexpected instance of FeignException", fe);
                throw new FisClientInterfaceException("An unknown error occurred while attempting to communicate with Fis Client");
        }
    }
}
