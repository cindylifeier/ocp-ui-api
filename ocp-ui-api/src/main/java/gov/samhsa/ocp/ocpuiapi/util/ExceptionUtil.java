package gov.samhsa.ocp.ocpuiapi.util;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.service.exception.client.FisClientInterfaceException;
import gov.samhsa.ocp.ocpuiapi.service.exception.location.LocationNotFoundException;
import gov.samhsa.ocp.ocpuiapi.service.exception.organization.OrganizationNotFoundException;
import gov.samhsa.ocp.ocpuiapi.service.exception.practitioner.PractitionerNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ExceptionUtil {

    public static void handleFeignExceptionRelatedToPractitionerSearch(FeignException fe, String logErrorMessage){
        int causedByStatus = fe.status();
        switch (causedByStatus) {
            case 404:
                String errorMessage = getErrorMessageFromFeignException(fe);
                String logErrorMessageWithCode = "Fis client returned a 404 - NOT FOUND status, indicating " + logErrorMessage;
                log.error(logErrorMessageWithCode, fe);
                throw new PractitionerNotFoundException(errorMessage);
            default:
                log.error("Fis client returned an unexpected instance of FeignException", fe);
                throw new FisClientInterfaceException("An unknown error occurred while attempting to communicate with Fis Client");
        }
    }

    public static void handleFeignExceptionRelatedToLocationSearch(FeignException fe, String logErrorMessage){
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

    public static String getErrorMessageFromFeignException(FeignException fe) {
        String detailMessage = fe.getMessage();
        String array[] = detailMessage.split("message");
        if (array.length > 1) {
            return array[1].substring(array[1].indexOf("\":\"") + 3, array[1].indexOf("\",\""));
        } else return detailMessage;
    }

    public static void handleFeignExceptionRelatedToOrganizationSearch(FeignException fe, String logErrorMessage) {

        int causedByStatus = fe.status();
        switch (causedByStatus) {
            case 404:
                String errorMessage = getErrorMessageFromFeignException(fe);
                String logErrorMessageWithCode = "Fis client returned a 404 - NOT FOUND status, indicating " + logErrorMessage;
                log.error(logErrorMessageWithCode, fe);
                throw new OrganizationNotFoundException(errorMessage);
            default:
                log.error("Fis client returned an unexpected instance of FeignException", fe);
                throw new FisClientInterfaceException("An unknown error occurred while attempting to communicate with Fis Client");
        }
    }
}
