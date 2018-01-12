package gov.samhsa.ocp.ocpuiapi.service.exception.organization;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrganizationNotFoundException  extends RuntimeException {
    public OrganizationNotFoundException() {
        super();
    }
    public OrganizationNotFoundException(String message) {
        super(message);
    }
}

