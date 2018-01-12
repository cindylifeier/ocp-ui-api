package gov.samhsa.ocp.ocpuiapi.service.exception.patient;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException() {
        super();
    }
    public PatientNotFoundException(String message) {
        super(message);
    }
}
