package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.OutlookCalendarDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis/outlook/calendar")
public class OutlookCalendarController {
    private final FisClient fisClient;

    public OutlookCalendarController(FisClient fisClient) {
        this.fisClient = fisClient;
    }

    @GetMapping
    public List<OutlookCalendarDto> getOutlookCalendarAppointments(@RequestParam(value = "emailAddress") String emailAddress,
                                                                   @RequestParam(value = "password") String password) {
        log.info("Searching for Outlook Appointments");
        try {
            return fisClient.getOutlookCalendarAppointments(emailAddress, password);
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignException(fe, "that no for Outlook Appointments were found");
            return null;
        }
    }
}
