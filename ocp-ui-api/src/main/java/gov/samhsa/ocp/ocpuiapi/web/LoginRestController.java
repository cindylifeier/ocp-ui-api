package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.service.LoginService;
import gov.samhsa.ocp.ocpuiapi.service.dto.CredentialDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public Object login(@Valid @RequestBody CredentialDto credentialDto) {
        return loginService.login(credentialDto);
    }
}
