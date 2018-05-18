package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.service.dto.OutlookCalendarDto;

import java.util.List;

public interface OutlookCalendarService {
    List<OutlookCalendarDto> getOutlookCalendarAppointments();
}
