package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.EwsCalendarDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis/EWS/calendar")
public class EwsCalendarController {
    private final FisClient fisClient;

    public EwsCalendarController(FisClient fisClient) {
        this.fisClient = fisClient;
    }

    @GetMapping
    public List<EwsCalendarDto> getEwsCalendarAppointments(@RequestParam(value = "emailAddress") String emailAddress,
                                                           @RequestParam(value = "password") String password) {
        return fisClient.getEwsCalendarAppointments(emailAddress, password);
    }
}
