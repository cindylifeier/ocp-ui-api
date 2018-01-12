package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ocp-fis/patients")
@Slf4j
public class PatientController {

    private final FisClient fisClient;

    public PatientController(FisClient fisClient) {
        this.fisClient = fisClient;
    }

    @GetMapping
    public Object getPatients() {
        log.debug("Call to Feign Client: START");
        try {
            Object patientDtos = fisClient.getPatients();
            log.debug("Call to Feign Client: END");
            return patientDtos;
        } catch (FeignException fe) {
            log.error("no patient were found in the configured FHIR server");
            return fe;
        }
    }

    @GetMapping("/search")
    public Object searchPatientsByValue(@RequestParam(value = "value") String value, @RequestParam(value = "type") String type, @RequestParam(value = "showInactive", defaultValue = "false") boolean showInactive) {
        log.debug("Call to Feign Client: START");
        Object patientDtos = fisClient.getPatientsByValue(value, type, showInactive);
        log.debug("Call to Feign Client: END");
        return patientDtos;

    }
}