package gov.samhsa.ocp.ocpuiapi.service.exception.practitioner;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PractitionerNotFoundException extends RuntimeException {
    public PractitionerNotFoundException() {
        super();
    }
    public PractitionerNotFoundException(String message) {
        super(message);
    }
}
