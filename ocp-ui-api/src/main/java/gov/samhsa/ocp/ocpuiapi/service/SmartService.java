package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.LaunchRequestDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.LaunchResponseDto;

public interface SmartService {
    LaunchResponseDto mergeAndSave(String launchId,
                                   LaunchRequestDto launchRequest);
}
