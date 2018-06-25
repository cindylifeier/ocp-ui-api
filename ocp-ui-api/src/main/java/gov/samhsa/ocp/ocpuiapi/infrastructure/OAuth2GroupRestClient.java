package gov.samhsa.ocp.ocpuiapi.infrastructure;

import gov.samhsa.ocp.ocpuiapi.config.OAuth2FeignClientCredentialsConfig;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupWrapperDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserWrapperDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.userinfo.UserInfoDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "oauth2GroupRestClient", url = "${ocp.ocp-ui-api.oauth2.authorization-server-endpoint}", configuration = OAuth2FeignClientCredentialsConfig.class)
public interface OAuth2GroupRestClient {

    @RequestMapping(value = "/Groups?count=1000", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GroupWrapperDto getAllGroups();

    @RequestMapping(value = "/Users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UserWrapperDto getAllUsers();

    @RequestMapping(value = "/userinfos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<UserInfoDto> getAllUserInfos();


}
