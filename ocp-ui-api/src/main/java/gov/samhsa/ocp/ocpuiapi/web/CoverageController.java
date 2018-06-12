package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.CoverageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ReferenceDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ocp-fis")
@Slf4j
public class CoverageController {

    @Autowired
    private FisClient fisClient;

    @PostMapping("/coverage")
    public void createCoverage(@Valid @RequestBody CoverageDto coverageDto) {
        log.info("About to create a coverage");
        try {
            fisClient.createCoverage(coverageDto);
            log.info("Successfully created a coverage");
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignException(fe, "that the coverage was not created");
        }
    }

    @GetMapping("/patients/{patientId}/subscriber-options")
    public List<ReferenceDto> getSubscriberOptions(@PathVariable String patientId){
        return fisClient.getSubscriberOptions(patientId);
    }

    @GetMapping("/patients/{patientId}/coverages")
    public Object getCoverages(@PathVariable String patientId, @RequestParam(value="pageNumber",required = false) Integer pageNumber,
                               @RequestParam(value="pageSize",required = false) Integer pageSize){
        log.info("Get coverages of the patient");
        try{
            return fisClient.getCoverages(patientId,pageNumber,pageSize);
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignException(fe,"coverages couldn't be fetch.");
            return null;
        }
    }
}
