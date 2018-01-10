package gov.samhsa.ocp.ocpuiapi.service;

public interface PractitionerService {

    /**
     * Gets all practitioners in the configured FHIR server
     *
     * @return
     */
    Object getAllPractitioners(Integer page, Integer size);
}
