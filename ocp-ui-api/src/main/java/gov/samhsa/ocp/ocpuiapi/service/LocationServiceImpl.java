package gov.samhsa.ocp.ocpuiapi.service;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.OcpFisClient;
import gov.samhsa.ocp.ocpuiapi.service.exception.client.OcpFisClientInterfaceException;
import gov.samhsa.ocp.ocpuiapi.service.exception.location.LocationNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

    @Autowired
    private OcpFisClient ocpFisClient;

    /**
     * Gets all available locations in the configured FHIR server
     *
     * @return
     */
    @Override
    public Object getAllLocations() {
        log.info("Fetching locations from FHIR Server...");
        try {
            Object ocpFisClientResponse = ocpFisClient.getAllLocations();
            log.info("Got response from FHIR Server...");
            return ocpFisClientResponse;
        }
        catch (FeignException fe) {
            if (fe.status() == 404) {
                log.error("Ocp-Fis client returned a 404 - NOT FOUND status, indicating no locations were found in the configured FHIR server", fe);
                throw new LocationNotFoundException("No locations were found in the configured FHIR server");
            } else {
                log.error("Ocp-Fis client returned an unexpected instance of FeignException", fe);
                throw new OcpFisClientInterfaceException("An unknown error occurred while attempting to communicate with Ocp-Fis Client");
            }
        }
    }

    /**
     * Gets all locations(all levels) that are managed under a given Organization Id
     *
     * @param organizationResourceId
     * @return
     */
    @Override
    public Object getLocationsByOrganization(String organizationResourceId) {
        log.info("Fetching locations from FHIR Server for the given OrganizationId: ", organizationResourceId);
        try {
            Object ocpFisClientResponse = ocpFisClient.getLocationsByOrganization(organizationResourceId);
            log.info("Got response from FHIR Server...");
            return ocpFisClientResponse;
        }
        catch (FeignException fe) {
            if (fe.status() == 404) {
                log.error("Ocp-Fis client returned a 404 - NOT FOUND status, indicating no locations were found in the configured FHIR server for the given OrganizationId", fe);
                throw new LocationNotFoundException("No locations were found in the configured FHIR server for the given OrganizationId" + organizationResourceId);
            } else {
                log.error("Ocp-Fis client returned an unexpected instance of FeignException", fe);
                throw new OcpFisClientInterfaceException("An unknown error occurred while attempting to communicate with Ocp-Fis Client");
            }
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
        log.info("Fetching locations from FHIR Server for the given OrganizationId: ", locationId);
        try {
            Object ocpFisClientResponse = ocpFisClient.getLocation(locationId);
            log.info("Got response from FHIR Server...");
            return ocpFisClientResponse;
        }
        catch (FeignException fe) {
            if (fe.status() == 404) {
                log.error("Ocp-Fis client returned a 404 - NOT FOUND status, indicating no location was found in the configured FHIR server for the given LocationId", fe);
                throw new LocationNotFoundException("No location was found in the configured FHIR server for the given LocationId:" + locationId);
            } else {
                log.error("Ocp-Fis client returned an unexpected instance of FeignException", fe);
                throw new OcpFisClientInterfaceException("An unknown error occurred while attempting to communicate with Ocp-Fis Client");
            }
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
        log.info("Fetching child location from FHIR Server for the given OrganizationId: ", locationId);
        try {
            Object ocpFisClientResponse = ocpFisClient.getChildLocation(locationId);
            log.info("Got response from FHIR Server...");
            return ocpFisClientResponse;
        }
        catch (FeignException fe) {
            if (fe.status() == 404) {
                log.error("Ocp-Fis client returned a 404 - NOT FOUND status, indicating no child location was found in the configured FHIR server for the given LocationId", fe);
                throw new LocationNotFoundException("No child location was found in the configured FHIR server for the given LocationId:" + locationId);
            } else {
                log.error("Ocp-Fis client returned an unexpected instance of FeignException", fe);
                throw new OcpFisClientInterfaceException("An unknown error occurred while attempting to communicate with Ocp-Fis Client");
            }
        }
    }
}
