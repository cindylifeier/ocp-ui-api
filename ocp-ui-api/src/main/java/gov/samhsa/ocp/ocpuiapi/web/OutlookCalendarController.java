package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.service.OutlookCalendarService;
import gov.samhsa.ocp.ocpuiapi.service.dto.OutlookCalendarDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis/outlook/calendar")
public class OutlookCalendarController {
    private final OutlookCalendarService outlookCalendarService;

    public OutlookCalendarController(OutlookCalendarService outlookCalendarService) {
        this.outlookCalendarService = outlookCalendarService;
    }

    @GetMapping
    public List<OutlookCalendarDto> getOutlookCalendarAppointments() {
        return outlookCalendarService.getOutlookCalendarAppointments();
    }
}
