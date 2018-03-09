package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.AppointmentDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ocp-fis/appointments")
@Slf4j
public class AppointmentController {
    @Autowired
    private FisClient fisClient;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        log.info("About to create an appointment");
        try {
            fisClient.createAppointment(appointmentDto);
            log.info("Successfully created an appointment");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, " that the appointment was not created");
        }
    }

    @GetMapping("/search")
    public Object getAppointments(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                  @RequestParam(value = "searchKey", required = false) String searchKey,
                                  @RequestParam(value = "searchValue", required = false) String searchValue,
                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Searching Appointments from FHIR server");
        try {
            Object appointment = fisClient.getAppointments(statusList, searchKey, searchValue, pageNumber, pageSize);
            log.info("Got Response from FHIR server for Appointment Search");
            return appointment;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No Appointments were found in configured FHIR server for the given searchKey and searchValue");
            return null;
        }
    }

    @PutMapping("/{appointmentId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancelAppointment(@PathVariable String appointmentId){
        try{
            fisClient.cancelAppointment(appointmentId);
            log.debug("Successfully cancelled the appointment.");
        }catch (FeignException fe){
            ExceptionUtil.handleFeignExceptionRelatedToResourceInactivation(fe,"Appointment could not be cancelled in the FHIR server");
        }
    }

}
