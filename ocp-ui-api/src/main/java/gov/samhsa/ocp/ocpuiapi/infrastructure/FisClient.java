package gov.samhsa.ocp.ocpuiapi.infrastructure;

import gov.samhsa.ocp.ocpuiapi.service.dto.ActivityDefinitionDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.CareTeamDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.HealthcareServiceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.IdentifierSystemDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.LocationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationStatusDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantSearchDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PatientDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.RelatedPersonDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ValueSetDto;
import gov.samhsa.ocp.ocpuiapi.web.PractitionerController;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "ocp-fis", url = "${ribbon.listOfServers}")
public interface FisClient {

    //LOCATIONS - START

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

    //LOCATIONS - END

    @RequestMapping(value = "/practitioners", method = RequestMethod.GET)
    PageDto<PractitionerDto> getAllPractitioners(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size);

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

    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    PageDto<OrganizationDto> getAllOrganizations(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                 @RequestParam(value = "page", required = false) Integer page,
                                                 @RequestParam(value = "size", required = false) Integer size);

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

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    Object getPatients();

    @RequestMapping(value = "/patients/search", method = RequestMethod.GET)
    Object getPatientsByValue(@RequestParam(value = "value") String value,
                              @RequestParam(value = "type") String type,
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
    PageDto<ParticipantSearchDto> getAllParticipants(@RequestParam(value = "member") String member,
                                                     @RequestParam(value = "value") String value,
                                                     @RequestParam(value = "showInActive", defaultValue = "false") Boolean showInActive,
                                                     @RequestParam(value = "page") Integer page,
                                                     @RequestParam(value = "size") Integer size);

    //HealthcareService - START

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
    void unassignsLocationFromHealthcareService(@PathVariable("healthcareServiceId") String healthcareServiceId,
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

    //HealthcareService - End

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
    PageDto<ActivityDefinitionDto> getAllActivityDefinitionsByOrganization(@PathVariable("organizationId") String organizationId,
                                                                         @RequestParam(value = "searchKey", required = false) String searchKey,
                                                                         @RequestParam(value = "searchValue", required = false) String searchValue,
                                                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/organization/{organizationId}/activity-definitions", method = RequestMethod.POST)
    void createActivityDefinition(@PathVariable("organizationId") String organizationId,
                        @Valid @RequestBody ActivityDefinitionDto activityDefinitionDto);

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

    //LOOKUP - START
    @RequestMapping(value = "/lookups/usps-states", method = RequestMethod.GET)
    List<ValueSetDto> getUspsStates();

    @RequestMapping(value = "/lookups/identifier-types", method = RequestMethod.GET)
    List<ValueSetDto> getIdentifierTypes(@RequestParam(value = "resourceType", required = false) String resourceType);

    @RequestMapping(value = "/lookups/identifier-systems", method = RequestMethod.GET)
    List<IdentifierSystemDto> getIdentifierSystems(@RequestParam(value = "identifierTypeList", required = false) List<String> identifierTypeList);

    @RequestMapping(value = "/lookups/identifier-uses", method = RequestMethod.GET)
    List<ValueSetDto> getIdentifierUses();

    @RequestMapping(value = "/lookups/location-modes", method = RequestMethod.GET)
    List<ValueSetDto> getLocationModes();

    @RequestMapping(value = "/lookups/location-statuses", method = RequestMethod.GET)
    List<ValueSetDto> getLocationStatuses();

    @RequestMapping(value = "/lookups/location-physical-types", method = RequestMethod.GET)
    List<ValueSetDto> getLocationPhysicalTypes();

    @RequestMapping(value = "/lookups/address-types", method = RequestMethod.GET)
    List<ValueSetDto> getAddressTypes();

    @RequestMapping(value = "/lookups/address-uses", method = RequestMethod.GET)
    List<ValueSetDto> getAddressUses();

    @RequestMapping(value = "/lookups/telecom-uses", method = RequestMethod.GET)
    List<ValueSetDto> getTelecomUses();

    @RequestMapping(value = "/lookups/telecom-systems", method = RequestMethod.GET)
    List<ValueSetDto> getTelecomSystems();

    @RequestMapping(value = "/lookups/organization-statuses", method = RequestMethod.GET)
    List<OrganizationStatusDto> getOrganizationStatuses();


    @RequestMapping(value = "/lookups/practitioner-roles", method = RequestMethod.GET)
    List<ValueSetDto> getPractitionerRoles();
    //LOOKUP - END

    @RequestMapping(value = "/lookups/administrative-genders", method = RequestMethod.GET)
    List<ValueSetDto> getAdministrativeGenders();

    @RequestMapping(value = "/lookups/us-core-races", method = RequestMethod.GET)
    List<ValueSetDto> getUSCoreRaces();

    @RequestMapping(value = "/lookups/us-core-ethnicities", method = RequestMethod.GET)
    List<ValueSetDto> getUSCoreEthnicities();

    @RequestMapping(value = "/lookups/us-core-birthsexes", method = RequestMethod.GET)
    List<ValueSetDto> getUSCoreBirthsexes();

    @RequestMapping(value = "/lookups/languages", method = RequestMethod.GET)
    List<ValueSetDto> getLanguages();

    @RequestMapping(value = "/lookups/healthcare-service-categories", method = RequestMethod.GET)
    List<ValueSetDto> getHealthcareServiceCategories();

    @RequestMapping(value = "/lookups/healthcare-service-types", method = RequestMethod.GET)
    List<ValueSetDto> getHealthcareServiceTypes();

    @RequestMapping(value = "/lookups/healthcare-service-specialities", method = RequestMethod.GET)
    List<ValueSetDto> getHealthcareServiceSpecialities();

    @RequestMapping(value = "/lookups/healthcare-service-referral-methods", method = RequestMethod.GET)
    List<ValueSetDto> getHealthcareServiceReferralMethods();

    @RequestMapping(value = "/lookups/care-team-categories", method = RequestMethod.GET)
    List<ValueSetDto> getCareTeamCategories();

    @RequestMapping(value = "/lookups/participant-types", method = RequestMethod.GET)
    List<ValueSetDto> getParticipantTypes();

    @RequestMapping(value = "/lookups/care-team-statuses", method = RequestMethod.GET)
    List<ValueSetDto> getCareTeamStatuses();

    @RequestMapping(value = "/lookups/care-team-reasons", method = RequestMethod.GET)
    List<ValueSetDto> getCareTeamReasons();


    @RequestMapping(value = "/lookups/participant-roles", method = RequestMethod.GET)
    List<ValueSetDto> getParticipantRoles();

    @RequestMapping(value = "/lookups/related-person-patient-relationship-types", method = RequestMethod.GET)
    List<ValueSetDto> getRelatedPersonPatientRelationshipTypes();

    @RequestMapping(value="/lookups/publication-status",method=RequestMethod.GET)
    List<ValueSetDto> getPublicationStatus();

    @RequestMapping(value="/lookups/definition-topic",method = RequestMethod.GET)
    List<ValueSetDto> getDefinitionTopic();

    @RequestMapping(value="lookups/resource-type",method=RequestMethod.GET)
    List<ValueSetDto> getResourceType();

    @RequestMapping(value="lookups/action-participant-role",method=RequestMethod.GET)
    List<ValueSetDto> getActionParticipantRole();

    @RequestMapping(value="lookups/action-participant-type",method=RequestMethod.GET)
    List<ValueSetDto> getActionParticipantType();

    //LOOKUP - END

}
