package gov.samhsa.ocp.ocpuiapi.config;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "ocp.ocp-ui-api")
@Data
public class ConfigProperties {
    @Valid
    @NotNull
    private OAuth2 oauth2;

    @Data
    public static class OAuth2 {
        @Valid
        @NotBlank
        private String authorizationServerEndpoint;
    }
}
