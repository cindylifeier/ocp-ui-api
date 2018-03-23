package gov.samhsa.ocp.ocpuiapi.infrastructure;

import gov.samhsa.ocp.ocpuiapi.service.dto.ActivityDefinitionDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.AppointmentDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.CareTeamDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.CommunicationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.HealthcareServiceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.LocationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantReferenceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantSearchDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PatientDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ReferenceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.RelatedPersonDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.TaskDto;
import gov.samhsa.ocp.ocpuiapi.web.PractitionerController;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "ocp-fis", url = "${ribbon.listOfServers}")
public interface FisClient {

    //Location

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    PageDto<LocationDto> getAllLocations(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                         @RequestParam(value = "searchKey", required = false) String searchKey,
                                         @RequestParam(value = "searchValue", required = false) String searchValue,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/organizations/{organizationId}/locations", method = RequestMethod.GET)
    PageDto<LocationDto> getLocationsByOrganization(@PathVariable("organizationId") String organizationId,
                                                    @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                    @RequestParam(value = "searchKey", required = false) String searchKey,
                                                    @RequestParam(value = "searchValue", required = false) String searchValue,
                                                    @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/locations/{locationId}", method = RequestMethod.GET)
    LocationDto getLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/locations/{locationId}/child-location", method = RequestMethod.GET)
    LocationDto getChildLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/organization/{organizationId}/location", method = RequestMethod.POST)
    void createLocation(@PathVariable("organizationId") String organizationId,
                        @Valid @RequestBody LocationDto locationDto);

    @RequestMapping(value = "/organization/{organizationId}/location/{locationId}", method = RequestMethod.PUT)
    void updateLocation(@PathVariable("organizationId") String organizationId,
                        @PathVariable("locationId") String locationId,
                        @Valid @RequestBody LocationDto locationDto);

    @RequestMapping(value = "/location/{locationId}/inactive", method = RequestMethod.PUT)
    void inactivateLocation(@PathVariable("locationId") String locationId);

    //Practitioner
    @RequestMapping(value = "/practitioners/search", method = RequestMethod.GET)
    PageDto<PractitionerDto> searchPractitioners(@RequestParam(value = "searchType", required = false) PractitionerController.SearchType searchType,
                                                 @RequestParam(value = "searchValue", required = false) String searchValue,
                                                 @RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/practitioners", method = RequestMethod.POST)
    void createPractitioner(@Valid @RequestBody PractitionerDto practitionerDto);

    @RequestMapping(value = "/practitioners/{practitionerId}", method = RequestMethod.PUT)
    void updatePractitioner(@PathVariable("practitionerId") String practitionerId, @Valid @RequestBody PractitionerDto practitionerDto);

    @RequestMapping(value = "/practitioners/{practitionerId}", method = RequestMethod.GET)
    PractitionerDto getPractitioner(@PathVariable("practitionerId") String practitionerId);

    @RequestMapping(value = "/practitioners", method = RequestMethod.GET)
    List<ReferenceDto> getPractitionersInOrganizationByPractitionerId(@RequestParam(value = "practitioner") String practitioner);

    @RequestMapping(value = "/practitioners/organization/{organizationId}")
    PageDto<PractitionerDto> getPractitionersByOrganizationAndRole(@PathVariable("organizationId") String organization,
                                                                   @RequestParam(value = "role", required = false) String role,
                                                                   @RequestParam(value = "page", required = false) Integer page,
                                                                   @RequestParam(value = "size", required = false) Integer size);

    //Organization
    @RequestMapping(value = "/organizations/all", method = RequestMethod.GET)
    PageDto<OrganizationDto> getOrganizations(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.GET)
    OrganizationDto getOrganization(@PathVariable("organizationId") String organizationId);

    @RequestMapping(value = "/organizations/search", method = RequestMethod.GET)
    PageDto<OrganizationDto> searchOrganizations(@RequestParam(value = "searchType", required = false) String searchType,
                                                 @RequestParam(value = "searchValue", required = false) String searchValue,
                                                 @RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/organizations", method = RequestMethod.POST)
    void createOrganization(@Valid @RequestBody OrganizationDto organizationDto);

    @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.PUT)
    void updateOrganization(@PathVariable("organizationId") String organizationId, @Valid @RequestBody OrganizationDto organizationDto);

    @RequestMapping(value = "/organizations/{organizationId}/inactive", method = RequestMethod.PUT)
    void inactivateOrganization(@PathVariable("organizationId") String organizationId);

    @RequestMapping(value = "/organizations")
    List<ReferenceDto> getOrganizationsByPractitioner(@RequestParam(value = "practitioner") String practitioner);

    //Patient

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    Object getPatients(@RequestParam(value = "practitioner", required = false) String practitioner,
                       @RequestParam(value = "searchKey", required = false) String searchKey,
                       @RequestParam(value = "searchValue", required = false) String searchValue,
                       @RequestParam(value = "showInActive", required = false) Boolean showInactive,
                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/patients/search", method = RequestMethod.GET)
    Object getPatientsByValue(@RequestParam(value = "type") String key,
                              @RequestParam(value = "value") String value,
                              @RequestParam(value = "showInactive", defaultValue = "false") boolean showInactive,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/patients", method = RequestMethod.POST)
    void createPatient(@Valid @RequestBody PatientDto patientDto);

    @RequestMapping(value = "/patients", method = RequestMethod.PUT)
    void updatePatient(@Valid @RequestBody PatientDto patientDto);

    @RequestMapping(value = "/patients/{patientId}", method = RequestMethod.GET)
    Object getPatientById(@PathVariable("patientId") String patientId);

    @RequestMapping(value = "/participants/search", method = RequestMethod.GET)
    PageDto<ParticipantSearchDto> getAllParticipants(@RequestParam(value = "patientId") String patientId,
                                                     @RequestParam(value = "member") String member,
                                                     @RequestParam(value = "value") String value,
                                                     @RequestParam(value = "showInActive", defaultValue = "false") Boolean showInActive,
                                                     @RequestParam(value = "page") Integer page,
                                                     @RequestParam(value = "size") Integer size);

    @RequestMapping(value = "/participants", method = RequestMethod.GET)
    List<ParticipantReferenceDto> getCareTeamParticipants(@RequestParam(value = "patient") String patient,
                                                          @RequestParam(value = "roles", required = false) List<String> roles,
                                                          @RequestParam(value = "communication", required = false) String communication);

    //HealthcareService

    @RequestMapping(value = "/healthcare-services", method = RequestMethod.GET)
    PageDto<HealthcareServiceDto> getAllHealthcareServices(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                                           @RequestParam(value = "searchKey", required = false) String searchKey,
                                                           @RequestParam(value = "searchValue", required = false) String searchValue,
                                                           @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/organizations/{organizationId}/healthcare-services", method = RequestMethod.GET)
    PageDto<HealthcareServiceDto> getAllHealthcareServicesByOrganization(@PathVariable("organizationId") String organizationId,
                                                                         @RequestParam(value = "assignedToLocationId", required = false) String assignedToLocationId,
                                                                         @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                         @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                         @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/healthcare-services/{healthcareServiceId}", method = RequestMethod.GET)
    HealthcareServiceDto getHealthcareService(@PathVariable("healthcareServiceId") String healthcareServiceId);

    @RequestMapping(value = "/organizations/{organizationId}/locations/{locationId}/healthcare-services", method = RequestMethod.GET)
    PageDto<HealthcareServiceDto> getAllHealthcareServicesByLocation(@PathVariable("organizationId") String organizationId,
                                                                     @PathVariable("locationId") String locationId,
                                                                     @RequestParam(value = "statusList", required = false) List<String> statusList,
                                                                     @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                     @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize);


    @RequestMapping(value = "/healthcare-services/{healthcareServiceId}/assign", method = RequestMethod.PUT)
    void assignLocationsToHealthcareService(@PathVariable("healthcareServiceId") String healthcareServiceId,
                                            @RequestParam(value = "organizationId") String organizationId,
                                            @RequestParam(value = "locationIdList") List<String> locationIdList);

    @RequestMapping(value = "/healthcare-services/{healthcareServiceId}/unassign", method = RequestMethod.PUT)
    void unassignLocationFromHealthcareService(@PathVariable("healthcareServiceId") String healthcareServiceId,
                                               @RequestParam(value = "organizationId") String organizationId,
                                               @RequestParam(value = "locationIdList") List<String> locationIdList);

    @RequestMapping(value = "/organization/{organizationId}/healthcare-service", method = RequestMethod.POST)
    void createHealthcareService(@PathVariable("organizationId") String organizationId,
                                 @Valid @RequestBody HealthcareServiceDto healthcareServiceDto);

    @RequestMapping(value = "/organization/{organizationId}/healthcare-service/{healthcareServiceId}", method = RequestMethod.PUT)
    void updateHealthcareService(@PathVariable("organizationId") String organizationId,
                                 @PathVariable("healthcareServiceId") String healthcareServiceId,
                                 @Valid @RequestBody HealthcareServiceDto healthcareServiceDto);

    @RequestMapping(value = "/healthcare-services/{healthcareServiceId}/inactive", method = RequestMethod.PUT)
    void inactivateHealthcareService(@PathVariable("healthcareServiceId") String healthcareServiceId);

    //CareTeam

    @RequestMapping(value = "/care-teams", method = RequestMethod.POST)
    void createCareTeam(@Valid @RequestBody CareTeamDto createTeamDto);

    @RequestMapping(value = "/care-teams/{careTeamId}", method = RequestMethod.PUT)
    void updateCareTeam(@PathVariable("careTeamId") String careTeamId, @Valid @RequestBody CareTeamDto careTeamDto);

    @RequestMapping(value = "/care-teams/search", method = RequestMethod.GET)
    PageDto<CareTeamDto> searchCareTeams(@RequestParam(value = "statusList", required = false) List<String> statusList,
                                         @RequestParam(value = "searchType", required = false) String searchType,
                                         @RequestParam(value = "searchValue", required = false) String searchValue,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping("/care-teams/{careTeamId}")
    CareTeamDto getCareTeamById(@PathVariable("careTeamId") String careTeamId);

    //Activity Definition

    @RequestMapping(value = "/organizations/{organizationId}/activity-definitions", method = RequestMethod.GET)
    Object getAllActivityDefinitionsByOrganization(@PathVariable("organizationId") String organizationId,
                                                   @RequestParam(value = "searchKey", required = false) String searchKey,
                                                   @RequestParam(value = "searchValue", required = false) String searchValue,
                                                   @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/organization/{organizationId}/activity-definitions", method = RequestMethod.POST)
    void createActivityDefinition(@PathVariable("organizationId") String organizationId,
                                  @Valid @RequestBody ActivityDefinitionDto activityDefinitionDto);

    @RequestMapping(value = "/activity-definitions", method = RequestMethod.GET)
    List<ReferenceDto> getActivityDefinitionsByPractitioner(@RequestParam(value = "practitioner") String practitioner);

    //Task

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    void createTask(@Valid @RequestBody TaskDto taskDto);

    @RequestMapping(value = "/tasks/search", method = RequestMethod.GET)
    Object searchTasks(@RequestParam(value = "statusList", required = false) List<String> statusList,
                       @RequestParam(value = "searchType", required = false) String searchType,
                       @RequestParam(value = "searchValue", required = false) String searchValue,
                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize);


    @RequestMapping(value = "/tasks/subtasks", method = RequestMethod.GET)
    List<TaskDto> getSubTasks(@RequestParam(value = "practitionerId", required = false) String practitionerId,
                              @RequestParam(value = "patientId", required = false) String patientId,
                              @RequestParam(value = "definition", required = false) String definition,
                              @RequestParam(value = "isUpcomingTasks", required = false) Boolean isUpcomingTasks);

    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.PUT)
    void updateTask(@PathVariable("taskId") String taskId, @Valid @RequestBody TaskDto taskDto);

    @RequestMapping(value = "/tasks/{taskId}/deactivate", method = RequestMethod.PUT)
    void deactivateTask(@PathVariable("taskId") String taskId);

    @RequestMapping(value = "/tasks/{taskId}")
    Object getTaskById(@PathVariable("taskId") String taskId);

    @RequestMapping(value = "/tasks/task-references", method = RequestMethod.GET)
    List<ReferenceDto> getRelatedTasks(@RequestParam(value = "patient") String patient, @RequestParam(value = "definition", required = false) String definition);

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    List<TaskDto> getMainAndSubTasks(@RequestParam(value = "practitionerId", required = false) String practitionerId,
                                     @RequestParam(value = "patientId", required = false) String patientId,
                                     @RequestParam(value = "definition", required = false) String definition,
                                     @RequestParam(value = "isUpcomingTasks", required = false) Boolean isUpcomingTasks);

    @RequestMapping(value="/tasks/upcoming-task-search", method=RequestMethod.GET)
    Object getUpcomingTasksByPractitionerAndRole(@RequestParam(value = "practitioner") String practitioner,
                                                                  @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                  @RequestParam(value = "searchValue",required = false) String searchValue,
                                                                  @RequestParam(value = "pageNumber",required = false) String pageNumber,
                                                                  @RequestParam(value = "pageSize",required = false) String pageSize);

    //RelatedPerson

    @RequestMapping(value = "/related-persons", method = RequestMethod.POST)
    void createRelatedPerson(@Valid @RequestBody RelatedPersonDto relatedPersonDto);

    @RequestMapping(value = "/related-persons/{relatedPersonId}", method = RequestMethod.PUT)
    void updateRelatedPerson(@PathVariable("relatedPersonId") String relatedPersonId, @Valid @RequestBody RelatedPersonDto relatedPersonDto);

    @RequestMapping(value = "/related-persons/search", method = RequestMethod.GET)
    PageDto<RelatedPersonDto> searchRelatedPersons(
            @RequestParam(value = "patientId") String patientId,
            @RequestParam(value = "searchKey", required = false) String searchKey,
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "showInActive", required = false) Boolean showInActive,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/related-persons/{relatedPersonId}")
    RelatedPersonDto getRelatedPersonById(@PathVariable("relatedPersonId") String relatedPersonId);

    @RequestMapping(value = "/episode-of-cares", method = RequestMethod.GET)
    List<ReferenceDto> getEpisodeOfCares(@RequestParam(value = "patient") String patient,
                                         @RequestParam(value = "status", required = false) String status);

    //Appointment

    @RequestMapping(value = "/appointments/search", method = RequestMethod.GET)
    Object getAppointments(@RequestParam(value = "statusList", required = false) List<String> statusList,
                           @RequestParam(value = "patientId", required = false) String patientId,
                           @RequestParam(value = "practitionerId", required = false) String practitionerId,
                           @RequestParam(value = "searchKey", required = false) String searchKey,
                           @RequestParam(value = "searchValue", required = false) String searchValue,
                           @RequestParam(value = "showPastAppointments", required = false) Boolean showPastAppointments,
                           @RequestParam(value = "sortByStartTimeAsc", required = false) Boolean sortByStartTimeAsc,
                           @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                           @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/appointments", method = RequestMethod.POST)
    void createAppointment(@Valid @RequestBody AppointmentDto appointmentDto);

    @RequestMapping(value = "/appointments/{appointmentId}/cancel", method = RequestMethod.PUT)
    void cancelAppointment(@PathVariable("appointmentId") String appointmentId);

    @RequestMapping(value = "/appointments/{appointmentId}", method = RequestMethod.PUT)
    void updateAppointment(@PathVariable("appointmentId") String appointmentId, @Valid @RequestBody AppointmentDto appointmentDto);

    @RequestMapping(value = "/appointments/{appointmentId}", method = RequestMethod.GET)
    AppointmentDto getAppointmentById(@PathVariable("appointmentId") String appointmentId);

    @RequestMapping(value = "/patients/{patientId}/appointmentParticipants", method = RequestMethod.GET)
    List<ParticipantReferenceDto> getAppointmentParticipants(@PathVariable("patientId") String patientId,
                                                             @RequestParam(value = "roles", required = false) List<String> roles,
                                                             @RequestParam(value = "appointmentId", required = false) String appointmentId);

    //Communication
    @RequestMapping(value = "/communications/search", method = RequestMethod.GET)
    Object getCommunications(@RequestParam(value = "statusList", required = false) List<String> statusList,
                             @RequestParam(value = "searchKey", required = false) String searchKey,
                             @RequestParam(value = "searchValue", required = false) String searchValue,
                             @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/communications", method = RequestMethod.POST)
    void createCommunication(@Valid @RequestBody CommunicationDto communicationDto);

    @RequestMapping(value = "/communications/{communicationsId}", method = RequestMethod.PUT)
    void updateCommunication(@PathVariable("communicationsId") String communicationsId, @Valid @RequestBody CommunicationDto communicationDto);
}
