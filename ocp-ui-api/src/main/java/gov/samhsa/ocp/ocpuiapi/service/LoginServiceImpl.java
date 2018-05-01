package gov.samhsa.ocp.ocpuiapi.service;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.config.OauthProperties;
import gov.samhsa.ocp.ocpuiapi.infrastructure.UaaFormEncodedClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.UaaRestClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.AutologinResponseDto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.CredentialDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.LoginResponseDto;
import gov.samhsa.ocp.ocpuiapi.service.exception.OauthClientConfigMissingException;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    private static final String GRAND_TYPE = "password";
    private static final String RESPONSE_TYPE = "token";
    private static final String CLIENT_ID = "ocp-ui";

    private final UaaFormEncodedClient uaaFormEncodedClient;
    private final UaaRestClient uaaRestClient;
    private final OauthProperties oauthProperties;

    @Autowired
    public LoginServiceImpl(UaaFormEncodedClient uaaFormEncodedClient, UaaRestClient uaaRestClient, OauthProperties oauthProperties) {
        this.uaaFormEncodedClient = uaaFormEncodedClient;
        this.uaaRestClient = uaaRestClient;
        this.oauthProperties = oauthProperties;
    }

    @Override
    public Object login(CredentialDto credentialDto) {
        LoginResponseDto loginResponse= null;
        try {
            Map<String, String> formParams = buildPasswordGrantFormParams(credentialDto);
            final Object authData = uaaFormEncodedClient.getTokenUsingPasswordGrant(formParams);
            OauthProperties.OauthClient ocpUiOauthClient = oauthProperties.getOauthClients().stream()
                    .filter(oauthClient -> oauthClient.getClientId().equalsIgnoreCase(CLIENT_ID))
                    .findAny()
                    .orElseThrow(OauthClientConfigMissingException::new);;
            final AutologinResponseDto autologin = uaaRestClient.getAutologin(credentialDto, "Basic " + Base64Utils.encodeToString(ocpUiOauthClient.getClientId().concat(":").concat(ocpUiOauthClient.getClientSecret()).getBytes(StandardCharsets.UTF_8)));
            loginResponse = new LoginResponseDto(authData, autologin);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionFailToLogin(fe, "User authentication failure by using username: ".concat(credentialDto.getUsername()));
        }
        return loginResponse;
    }

    private Map<String, String> buildPasswordGrantFormParams(CredentialDto credentialDto) {
        OauthProperties.OauthClient ocpUiOauthClient = oauthProperties.getOauthClients().stream()
                .filter(oauthClient -> oauthClient.getClientId().equalsIgnoreCase(CLIENT_ID))
                .findAny()
                .orElseThrow(OauthClientConfigMissingException::new);

        Map<String, String> formParams = new HashMap<>();
        formParams.put("client_id", ocpUiOauthClient.getClientId());
        formParams.put("client_secret", ocpUiOauthClient.getClientSecret());
        formParams.put("grant_type", GRAND_TYPE);
        formParams.put("response_type", RESPONSE_TYPE);
        formParams.put("username", credentialDto.getUsername());
        formParams.put("password", credentialDto.getPassword());
        return formParams;
    }
}
