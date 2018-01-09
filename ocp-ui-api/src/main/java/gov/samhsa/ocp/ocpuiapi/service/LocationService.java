package gov.samhsa.ocp.ocpuiapi.service;

public interface LocationService {

    /**
     * Gets all available locations in the configured FHIR server
     *
     * @return
     */
    Object getAllLocations(Integer page, Integer size);

    /**
     * Gets all locations(all levels) that are managed under a given Organization Id
     *
     * @param organizationResourceId
     * @return
     */
    Object getLocationsByOrganization(String organizationResourceId, Integer page, Integer size);

    /**
     * Get Location By Id
     *
     * @param locationId
     * @return
     */
    Object getLocation(String locationId);

    /**
     * Gets level 1 child location for a given Location Id
     *
     * @param locationId
     * @return
     */
    Object getChildLocation(String locationId);
}
