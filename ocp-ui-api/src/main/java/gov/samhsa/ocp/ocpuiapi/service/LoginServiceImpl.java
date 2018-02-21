package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.config.OauthProperties;
import gov.samhsa.ocp.ocpuiapi.infrastructure.UaaClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.CredentialDto;
import gov.samhsa.ocp.ocpuiapi.service.exception.OauthClientConfigMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    private static final String GRAND_TYPE = "password";
    private static final String RESPONSE_TYPE = "token";
    private static final String CLIENT_ID = "ocp-ui";

    private final UaaClient uaaClient;
    private final OauthProperties oauthProperties;

    @Autowired
    public LoginServiceImpl(UaaClient uaaClient, OauthProperties oauthProperties) {
        this.uaaClient = uaaClient;
        this.oauthProperties = oauthProperties;
    }

    @Override
    public Object login(CredentialDto credentialDto) {
        Map<String, String> formParams = buildPasswordGrantFormParams(credentialDto);
        return uaaClient.getTokenUsingPasswordGrant(formParams);
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
