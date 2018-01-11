package gov.samhsa.ocp.ocpuiapi.service;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.OcpFisClient;
import gov.samhsa.ocp.ocpuiapi.service.exception.client.OcpFisClientInterfaceException;
import gov.samhsa.ocp.ocpuiapi.service.exception.practitioner.PractitionerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PractitionerServiceImpl implements PractitionerService{

    @Autowired
    private OcpFisClient ocpFisClient;

    /**
     * Gets all available practitioners in the configured FHIR server
     *
     * @return
     */
    @Override
    public Object getAllPractitioners(Integer page, Integer size) {
        log.info("Fetching practitioners from FHIR Server...");
        try {
            Object ocpFisClientResponse = ocpFisClient.getAllPractitioners(page, size);
            log.info("Got response from FHIR Server...");
            return ocpFisClientResponse;
        }
        catch (FeignException fe) {
            handleFeignExceptionRelatedToPractitionerSearch(fe, "no practitioners were found in the configured FHIR server");
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

    private void handleFeignExceptionRelatedToPractitionerSearch(FeignException fe, String logErrorMessage){
        int causedByStatus = fe.status();
        switch (causedByStatus) {
            case 404:
                String errorMessage = getErrorMessageFromFeignException(fe);
                String logErrorMessageWithCode = "Ocp-Fis client returned a 404 - NOT FOUND status, indicating " + logErrorMessage;
                log.error(logErrorMessageWithCode, fe);
                throw new PractitionerNotFoundException(errorMessage);
            default:
                log.error("Ocp-Fis client returned an unexpected instance of FeignException", fe);
                throw new OcpFisClientInterfaceException("An unknown error occurred while attempting to communicate with Ocp-Fis Client");
        }
    }


}
