package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.CommunicationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ConsentDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("ocp-fis")
@Slf4j
public class ConsentController {
    @Autowired
    private FisClient fisClient;

    @PostMapping("/consents")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCommunication(@Valid @RequestBody ConsentDto consentDto) {
        log.info("About to create a consent");
        try {
            fisClient.createConsent(consentDto);
            log.info("Successfully created a consent.");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, " that the consent was not created");
        }
    }
}
