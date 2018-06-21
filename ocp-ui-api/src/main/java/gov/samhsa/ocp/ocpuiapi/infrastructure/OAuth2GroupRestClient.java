package gov.samhsa.ocp.ocpuiapi.infrastructure;

import gov.samhsa.ocp.ocpuiapi.config.OAuth2FeignClientCredentialsConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "oauth2GroupRestClient", url = "${ocp.ocp-ui-api.oauth2.authorization-server-endpoint}", configuration = OAuth2FeignClientCredentialsConfig.class)
public interface OAuth2GroupRestClient {

    @RequestMapping(value = "/Groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object getAllGroups();

    @RequestMapping(value = "/Users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Object getAllUsersByOrganization();

}
