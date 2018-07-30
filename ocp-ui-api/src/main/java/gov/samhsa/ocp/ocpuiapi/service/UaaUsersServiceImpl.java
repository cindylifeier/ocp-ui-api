package gov.samhsa.ocp.ocpuiapi.service;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.UaaUserTokenRestClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.JwtTokenKey;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordResponseDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UaaUsersServiceImpl implements UaaUsersService {

    private final JwtTokenExtractor jwtTokenExtractor;

    private final UaaUserTokenRestClient uaaUserTokenRestClient;

    @Autowired
    public UaaUsersServiceImpl(JwtTokenExtractor jwtTokenExtractor, UaaUserTokenRestClient uaaUserTokenRestClient) {
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.uaaUserTokenRestClient = uaaUserTokenRestClient;
    }

    @Override
    public ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        String userId = jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID).toString();
        Assert.hasText(userId, "user_id must have text");
        ChangePasswordResponseDto changePasswordResponseDto = null;
        try {
            changePasswordResponseDto = uaaUserTokenRestClient.changePassword(userId, changePasswordRequestDto);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionFailToLogin(fe, "User change password failure with userId: ".concat(userId));
        }
        return changePasswordResponseDto;
    }
}
