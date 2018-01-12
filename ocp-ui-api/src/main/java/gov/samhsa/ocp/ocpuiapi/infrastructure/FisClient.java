package gov.samhsa.ocp.ocpuiapi.infrastructure;

import gov.samhsa.ocp.ocpuiapi.service.dto.LocationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.web.OrganizationController;
import gov.samhsa.ocp.ocpuiapi.web.PractitionerController;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ocp-fis", url = "${ribbon.listOfServers}")
public interface FisClient {

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    PageDto<LocationDto> getAllLocations(@RequestParam(value = "status", required = false)List<String> status,
                                         @RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/organizations/{organizationId}/locations", method = RequestMethod.GET)
    PageDto<LocationDto> getLocationsByOrganization(@PathVariable("organizationId") String organizationId,
                                      @RequestParam(value = "status", required = false)List<String> status,
                                      @RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/locations/{locationId}", method = RequestMethod.GET)
    LocationDto getLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/locations/{locationId}/childLocation", method = RequestMethod.GET)
    LocationDto getChildLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/practitioners", method = RequestMethod.GET)
    List<PractitionerDto> getAllPractitioners(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/practitioners/search", method = RequestMethod.GET)
    List<PractitionerDto> searchPractitioners(@RequestParam(value = "searchType", required = false) PractitionerController.SearchType searchType,
                                              @RequestParam(value = "searchValue", required = false) String searchValue,
                                              @RequestParam(value = "showInactive", required = false) boolean showInactive,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/organizations", method = RequestMethod.GET)
    List<OrganizationDto> getAllOrganizations(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/organizations/search", method = RequestMethod.GET)
    List<OrganizationDto> searchOrganizations(@RequestParam(value = "searchType", required = false) OrganizationController.SearchType searchType,
                                              @RequestParam(value = "searchValue", required = false) String searchValue,
                                              @RequestParam(value = "showInactive", required = false) boolean showInactive,
                                              @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "size", required = false) Integer size);


    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public Object getPatients();

    @RequestMapping(value = "/patients/search", method = RequestMethod.GET)
    public Object getPatientsByValue(@RequestParam(value = "value") String value, @RequestParam(value = "type") String type, @RequestParam(value = "showInactive", defaultValue = "false") boolean showInactive);


}
