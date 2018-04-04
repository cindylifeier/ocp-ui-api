package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.ConsentDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("ocp-fis")
@Slf4j
public class ConsentController {
    @Autowired
    FisClient fisClient;

    @GetMapping("/consents")
    public Object getConsents(@RequestParam(value = "patient", required = false) String patient,
                              @RequestParam(value = "practitioner", required = false) String practitioner,
                              @RequestParam(value = "status", required = false) String status,
                              @RequestParam(value = "generalDesignation", required = false) Boolean generalDesignation,
                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Searching Consents from FHIR server");
        try {
            Object consents = fisClient.getConsents(patient, practitioner, status, generalDesignation, pageNumber, pageSize);
            log.info("Got Response from FHIR server for Consents Search");
            return consents;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No Consents were found in configured FHIR server");
            return null;
        }
    }

    @GetMapping("/consents/{consentId}")
    public Object getConsentById(@PathVariable String consentId) {
        log.info("Fetching consent from FHIR Server for the given consentId: " + consentId);
        try {
            Object fisClientResponse = fisClient.getConsentById(consentId);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "the consent was not found");
            return null;
        }
    }
}
