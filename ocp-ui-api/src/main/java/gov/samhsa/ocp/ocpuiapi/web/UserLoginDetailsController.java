package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.service.UserLoginDetailsService;
import gov.samhsa.ocp.ocpuiapi.service.dto.UserLoginDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginDetailsController {
    @Autowired
    UserLoginDetailsService userLoginDetailsService;

    @GetMapping("/sample-user-login-details")
    public UserLoginDetailsDto getUserResource() {
        return userLoginDetailsService.getUserLoginDetails();
    }
}
