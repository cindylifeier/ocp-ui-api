package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.AppointmentDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantReferenceDto;
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
@RequestMapping("ocp-fis")
@Slf4j
public class AppointmentController {
    private final FisClient fisClient;

    @Autowired
    public AppointmentController(FisClient fisClient) {
        this.fisClient = fisClient;
    }

    @PostMapping("/appointments")
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

    @GetMapping("/appointments/search")
    public Object getAppointments(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                  @RequestParam(value = "patientId", required = false) String patientId,
                                  @RequestParam(value = "practitionerId", required = false) String practitionerId,
                                  @RequestParam(value = "searchKey", required = false) String searchKey,
                                  @RequestParam(value = "searchValue", required = false) String searchValue,
                                  @RequestParam(value = "showPastAppointments", required = false) Boolean showPastAppointments,
                                  @RequestParam(value = "sortByStartTimeAsc", required = false, defaultValue = "true") Boolean sortByStartTimeAsc,
                                  @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Searching Appointments from FHIR server");
        try {
            Object appointment = fisClient.getAppointments(statusList, patientId, practitionerId, searchKey, searchValue, showPastAppointments, sortByStartTimeAsc, pageNumber, pageSize);
            log.info("Got Response from FHIR server for Appointment Search");
            return appointment;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No Appointments were found in configured FHIR server for the given searchKey and searchValue");
            return null;
        }
    }

    @PutMapping("/appointments/{appointmentId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancelAppointment(@PathVariable String appointmentId) {
        try {
            fisClient.cancelAppointment(appointmentId);
            log.debug("Successfully cancelled the appointment.");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceInactivation(fe, "the appointment could not be cancelled.");
        }
    }

    @PutMapping("/appointments/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAppointment(@PathVariable String appointmentId, @Valid @RequestBody AppointmentDto appointmentDto) {
        log.info("About to update the appointment ID: " + appointmentId);
        try {
            fisClient.updateAppointment(appointmentId, appointmentDto);
            log.info("Successfully updated the appointment ID: " + appointmentId);
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceUpdate(fe, "the appointment was not updated");
        }
    }

    @GetMapping("/appointments/{appointmentId}")
    public AppointmentDto getAppointmentById(@PathVariable String appointmentId) {
        log.info("Fetching appointment from FHIR Server for the given appointmentId: " + appointmentId);
        try {
            AppointmentDto fisClientResponse = fisClient.getAppointmentById(appointmentId);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "the appointment was not found");
            return null;
        }
    }

    @GetMapping("/patients/{patientId}/appointmentParticipants")
    public List<ParticipantReferenceDto> getAppointmentParticipants(@PathVariable String patientId,
                                                             @RequestParam(value = "roles", required = false) List<String> roles,
                                                             @RequestParam(value = "appointmentId", required = false) String appointmentId) {

        log.info("Fetching appointment participants from FHIR Server for the given PatientId: " + patientId);
        try {
            List<ParticipantReferenceDto> fisClientResponse = fisClient.getAppointmentParticipants(patientId, roles, appointmentId);
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no participants were found for the given patient and the roles");
            return null;
        }
    }
}

