package gov.samhsa.ocp.ocpuiapi.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "ocp.ocp-ui-api.oauth2")
@Data
public class OAuth2Properties {

    @Valid
    @NotNull
    public List<OAuth2Client> oauth2Clients;

    @Data
    public static class OAuth2Client {
        @NotBlank
        private String clientId;

        @NotBlank
        @JsonSerialize(using = MaskedSerializer.class)
        private String clientSecret;
    }

    public static class MaskedSerializer extends JsonSerializer<String> {
        @Override
        public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString("********");
        }
    }
}
