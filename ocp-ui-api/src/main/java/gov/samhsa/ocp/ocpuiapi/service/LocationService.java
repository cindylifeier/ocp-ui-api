package gov.samhsa.ocp.ocpuiapi.service;

import java.util.List;

public interface LocationService {

    /**
     *
     * @param status
     * @param page
     * @param size
     * @return
     */
    Object getAllLocations(List<String> status, Integer page, Integer size);

    /**
     *
     * @param organizationResourceId
     * @param status
     * @param page
     * @param size
     * @return
     */
    Object getLocationsByOrganization(String organizationResourceId, List<String> status, Integer page, Integer size);

    /**
     *
     * @param locationId
     * @return
     */
    Object getLocation(String locationId);

    /**
     * Gets level 1 child location for a given Location Id
     * @param locationId
     * @return
     */
    Object getChildLocation(String locationId);
}
