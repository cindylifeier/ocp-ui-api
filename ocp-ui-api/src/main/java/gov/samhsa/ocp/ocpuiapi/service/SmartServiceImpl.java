package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.infrastructure.SmartCoreClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.LaunchRequestDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.LaunchResponseDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.JwtTokenKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SmartServiceImpl implements SmartService {
    @Autowired
    private JwtTokenExtractor jwtTokenExtractor;
    @Autowired
    private SmartCoreClient smartCoreClient;

    @Override
    public LaunchResponseDto mergeAndSave(String launchId,
                                          LaunchRequestDto launchRequest) {
        Assert.hasText(launchId, "launch ID must have text");
        Assert.notNull(launchRequest, "launchRequest cannot be null");
        final String userId = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID).toString();
        Assert.hasText(userId, "user_id must have text");
        launchRequest.setUser(userId);
        return smartCoreClient.mergeAndSave(launchId, launchRequest);
    }
}
