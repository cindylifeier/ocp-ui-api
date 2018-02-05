package gov.samhsa.ocp.ocpuiapi.infrastructure;

import gov.samhsa.ocp.ocpuiapi.service.dto.CareTeamDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.IdentifierSystemDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.LocationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationStatusDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantSearchDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PatientDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ValueSetDto;
import gov.samhsa.ocp.ocpuiapi.web.PractitionerController;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public PageDto<ParticipantSearchDto> getAllParticipants(@RequestParam(value = "member") String member,
                                                            @RequestParam(value = "value") String value,
                                                            @RequestParam(value = "showInActive", defaultValue = "false") Boolean showInActive,
                                                            @RequestParam(value = "page") Integer page,
                                                            @RequestParam(value = "size") Integer size);
    //HealthCareService - START
    @RequestMapping(value = "/health-care-services/{healthCareServiceId}/assign", method = RequestMethod.PUT)
    public void assignLocationToHealthCareService(@PathVariable("healthCareServiceId") String healthCareServiceId,
                                                  @RequestParam(value = "organizationId") String organizationId,
                                                  @RequestParam(value = "locationIdList") List<String> locationIdList);

    //HealthCareService - End

    //CareTeam
    @RequestMapping(value = "/careTeams", method = RequestMethod.POST)
    void createCareTeam(@Valid @RequestBody CareTeamDto createTeamDto);

    @RequestMapping(value = "/careTeams/{careTeamId}", method = RequestMethod.PUT)
    void updateCareTeam(@PathVariable("careTeamId") String careTeamId, @Valid @RequestBody CareTeamDto careTeamDto);

    @RequestMapping(value="/careTeams/search",method=RequestMethod.GET)
    PageDto<CareTeamDto> searchCareTeams(@RequestParam(value="statusList",required = false) List<String> statusList,
                                         @RequestParam(value="searchType",required = false) String searchType,
                                         @RequestParam(value="searchValue",required = false) String searchValue,
                                         @RequestParam(value="pageNumber",required = false) Integer pageNumber,
                                         @RequestParam(value="pageSize",required = false) Integer pageSize);

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

    @RequestMapping(value = "/lookups/healthcareservice-categories", method = RequestMethod.GET)
    List<ValueSetDto> getHealthCareServiceCategories();

    @RequestMapping(value = "/lookups/healthcareservice-types", method = RequestMethod.GET)
    List<ValueSetDto> getHealthCareServiceTypes();

    @RequestMapping(value = "/lookups/care-team-categories", method = RequestMethod.GET)
    List<ValueSetDto> getCareTeamCategories();

    @RequestMapping(value = "/lookups/participant-types", method = RequestMethod.GET)
    List<ValueSetDto> getParticipantTypes();

    @RequestMapping(value = "/lookups/care-team-statuses", method = RequestMethod.GET)
    List<ValueSetDto> getCareTeamStatuses();

    @RequestMapping(value = "/lookups/participant-roles", method = RequestMethod.GET)
    List<ValueSetDto> getParticipantRoles();

    //LOOKUP - END

}
