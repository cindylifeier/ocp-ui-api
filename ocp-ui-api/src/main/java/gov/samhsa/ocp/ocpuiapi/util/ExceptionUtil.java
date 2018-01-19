package gov.samhsa.ocp.ocpuiapi.util;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.service.dto.ResourceType;
import gov.samhsa.ocp.ocpuiapi.service.exception.FisClientInterfaceException;
import gov.samhsa.ocp.ocpuiapi.service.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ExceptionUtil {

    public static void handleFeignExceptionRelatedToSearch(FeignException fe, String logErrorMessage, String resourceType) {
        int causedByStatus = fe.status();
        switch (causedByStatus) {
            case 404:
                String errorMessage = getErrorMessageFromFeignException(fe);
                String logErrorMessageWithCode = "Fis client returned a 404 - NOT FOUND status, indicating " + errorMessage;
                log.error(logErrorMessageWithCode, fe);
                if (resourceType.equalsIgnoreCase(ResourceType.PRACTITIONER.name()))
                    throw new ResourceNotFoundException(errorMessage);
                else if (resourceType.equalsIgnoreCase(ResourceType.LOCATION.name()))
                    throw new ResourceNotFoundException(errorMessage);
                else if (resourceType.equalsIgnoreCase(ResourceType.ORGANIZATION.name()))
                    throw new ResourceNotFoundException(errorMessage);
                else if (resourceType.equalsIgnoreCase(ResourceType.PATIENT.name()))
                    throw new ResourceNotFoundException(errorMessage);
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
        } else {
            return detailMessage;
        }
    }

}