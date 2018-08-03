package gov.samhsa.ocp.ocpuiapi.service;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.OAuth2GroupRestClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.UaaUserTokenRestClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.JwtTokenKey;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ChangePasswordResponseDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.ResetPasswordRequestDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UaaUsersServiceImpl implements UaaUsersService {

    private static final String OCP_ROLE_ORGANIZATION_ADMINISTRATOR = "ocp.role.organizationAdministrator";
    private static final String OCP_ROLE_OCP_ADMIN = "ocp.role.ocpAdmin";
    private final JwtTokenExtractor jwtTokenExtractor;
    private final UaaUserTokenRestClient uaaUserTokenRestClient;
    private final OAuth2GroupRestClient oAuth2GroupRestClient;

    @Autowired
    public UaaUsersServiceImpl(JwtTokenExtractor jwtTokenExtractor, UaaUserTokenRestClient uaaUserTokenRestClient, OAuth2GroupRestClient oAuth2GroupRestClient) {
        this.jwtTokenExtractor = jwtTokenExtractor;
        this.uaaUserTokenRestClient = uaaUserTokenRestClient;
        this.oAuth2GroupRestClient = oAuth2GroupRestClient;
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

    @Override
    public ChangePasswordResponseDto resetPassword(String userId, ResetPasswordRequestDto resetPasswordRequestDto) {
        List<String> scopes = (List<String>) jwtTokenExtractor.getValueByKey(JwtTokenKey.SCOPE);
        boolean isOrgOrOcpAdmin = scopes.contains(OCP_ROLE_ORGANIZATION_ADMINISTRATOR) || scopes.contains(OCP_ROLE_OCP_ADMIN);
        Assert.hasText(userId, "user_id must have text");
        Assert.isTrue(isOrgOrOcpAdmin, "This user does not allow to do resetting password.");
        ChangePasswordResponseDto changePasswordResponseDto = null;
        try {
            changePasswordResponseDto = oAuth2GroupRestClient.resetPassword(userId, resetPasswordRequestDto);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionFailToLogin(fe, "Reset User password failure for userId: ".concat(userId));
        }
        return changePasswordResponseDto;
    }
}
