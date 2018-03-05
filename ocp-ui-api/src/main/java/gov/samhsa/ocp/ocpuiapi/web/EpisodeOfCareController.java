package gov.samhsa.ocp.ocpuiapi.web;


import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.EpisodeOfCareDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ocp-fis/episode-of-cares")
public class EpisodeOfCareController {

    @Autowired
    private FisClient fisClient;

    @GetMapping
    private List<EpisodeOfCareDto> getEpisodeOfCares(@RequestParam(value = "patient") String patient,
                                                     @RequestParam(value = "status", required = false) String status) {
        try {
            return fisClient.getEpisodeOfCares(patient, status);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No EpisodeOfCare was found for the given patient Id");
            return null;
        }
    }
}
