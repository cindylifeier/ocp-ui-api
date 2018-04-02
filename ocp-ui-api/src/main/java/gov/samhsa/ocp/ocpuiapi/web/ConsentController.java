package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
                              @RequestParam(value = "fromActor", required = false) String fromActor,
                              @RequestParam(value = "toActor", required = false) String toActor,
                              @RequestParam(value = "generalDesignation", required = false) Boolean generalDesignation,
                              @RequestParam(value = "status", required = false) String status,
                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Searching Consents from FHIR server");
        try {
            Object consents = fisClient.getConsents(patient, fromActor, toActor,generalDesignation, status, pageNumber, pageSize);
            log.info("Got Response from FHIR server for Consents Search");
            return consents;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No Consents were found in configured FHIR server");
            return null;
        }
    }
}
