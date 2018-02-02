package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis")
public class HealthCareServiceController {
    @Autowired
    private FisClient fisClient;

    @PutMapping("/health-care-services/{healthCareServiceId}/assign")
    @ResponseStatus(HttpStatus.OK)
    public void assignLocationToHealthCareService(@PathVariable String healthCareServiceId,
                                                  @RequestParam(value = "organizationId") String organizationId,
                                                  @RequestParam(value = "locationIdList") List<String> locationIdList) {
        log.info("About to assign locations to the health care service...");
        try {
        fisClient.assignLocationToHealthCareService(healthCareServiceId, organizationId, locationIdList);
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedAssigningLocToHealthCareService(fe, " the location(s) were not assigned to the health care service.");
        }
        log.info("Successfully assigned all locations to the health care service.");
    }
}
