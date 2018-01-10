package gov.samhsa.ocp.ocpuiapi.service;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.exception.client.FisClientInterfaceException;
import gov.samhsa.ocp.ocpuiapi.service.exception.location.LocationNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

    private FisClient fisClient;

    @Autowired
    public LocationServiceImpl(FisClient fisClient) {
        this.fisClient = fisClient;
    }

    /**
     * Gets all available locations in the configured FHIR server
     *
     * @return
     */
    @Override
    public Object getAllLocations(Integer page, Integer size) {
        log.info("Fetching locations from FHIR Server...");
        try {
            Object fisClientResponse = fisClient.getAllLocations(page, size);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            handleFeignExceptionRelatedToLocationSearch(fe, "no locations were found in the configured FHIR server");
            return null;
        }
    }

    /**
     * Gets all locations(all levels) that are managed under a given Organization Id
     *
     * @param organizationResourceId
     * @return
     */
    @Override
    public Object getLocationsByOrganization(String organizationResourceId, Integer page, Integer size) {
        log.info("Fetching locations from FHIR Server for the given OrganizationId: "+ organizationResourceId);
        try {
            Object fisClientResponse = fisClient.getLocationsByOrganization(organizationResourceId, page, size);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            handleFeignExceptionRelatedToLocationSearch(fe, "no locations were found in the configured FHIR server for the given OrganizationId");
            return null;
        }
    }

    /**
     * Get Location By Id
     *
     * @param locationId
     * @return
     */
    @Override
    public Object getLocation(String locationId) {
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

    /**
     * Gets level 1 child location for a given Location Id
     *
     * @param locationId
     * @return
     */
    @Override
    public Object getChildLocation(String locationId) {
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
