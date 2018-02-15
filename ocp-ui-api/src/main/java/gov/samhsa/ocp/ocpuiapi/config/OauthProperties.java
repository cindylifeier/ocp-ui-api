package gov.samhsa.ocp.ocpuiapi.config;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "ocp.ocp-ui-api.oauth")
@Data
public class OauthProperties {

    @Valid
    @NotNull
    public List<OauthClient> oauthClients;

    @Data
    public static class OauthClient {
        @NotBlank
        private String clientId;

        @NotBlank
        private String clientSecret;
    }
}
