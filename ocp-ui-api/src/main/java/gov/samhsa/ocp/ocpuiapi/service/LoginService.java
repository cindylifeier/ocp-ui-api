package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.service.dto.CredentialDto;

public interface LoginService {
    Object login(CredentialDto credentialDto);
}
