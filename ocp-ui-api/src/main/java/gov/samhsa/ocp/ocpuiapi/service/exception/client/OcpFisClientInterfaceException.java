package gov.samhsa.ocp.ocpuiapi.service.exception.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class OcpFisClientInterfaceException extends RuntimeException {
    public OcpFisClientInterfaceException() {
        super();
    }

    public OcpFisClientInterfaceException(String message) {
        super(message);
    }
}
