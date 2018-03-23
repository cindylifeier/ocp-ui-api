package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ReferenceDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis")
public class PractitionerController {

    public enum SearchType {
        identifier, name
    }

    @Autowired
    private FisClient fisClient;

    /**
     * Example: http://localhost:8446/ocp-fis/practitioners/search?searchType=name&searchValue=smith&showInactive=true&page=1&size=10
     *
     * @param searchType
     * @param searchValue
     * @param showInactive
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/practitioners/search")
    public PageDto<PractitionerDto> searchPractitioners(@RequestParam(value = "searchType", required = false) SearchType searchType,
                                                        @RequestParam(value = "searchValue", required = false) String searchValue,
                                                        @RequestParam(value = "showInactive", required = false) Boolean showInactive,
                                                        @RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "size", required = false) Integer size) {
        log.info("Searching practitioners from FHIR server");
        try {
            PageDto<PractitionerDto> practitioners = fisClient.searchPractitioners(searchType, searchValue, showInactive, page, size);
            log.info("Got response from FHIR server for practitioner search");
            return practitioners;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no practitioners were found in the configured FHIR server for the given searchType and searchValue");
            return null;
        }
    }

    @PostMapping("/practitioners")
    @ResponseStatus(HttpStatus.CREATED)
    void createPractitioner(@Valid @RequestBody PractitionerDto practitionerDto) {
        try {

            fisClient.createPractitioner(practitionerDto);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, " that the practitioner was not created");
        }
    }

    @PutMapping("/practitioners/{practitionerId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePractitioner(@PathVariable String practitionerId, @Valid @RequestBody PractitionerDto practitionerDto) {
        try {
            fisClient.updatePractitioner(practitionerId, practitionerDto);

        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceUpdate(fe, " that the practitioner was not updated");
        }
    }

    @GetMapping("/practitioners/{practitionerId}")
    public PractitionerDto getPractitioner(@PathVariable String practitionerId) {
        try {
            return fisClient.getPractitioner(practitionerId);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No practitioner was found");
            return null;
        }
    }

    @GetMapping("/practitioners")
    public List<ReferenceDto> getPractitionersInOrganizationByPractitionerId(@RequestParam(value = "practitioner") String practitioner) {
        try {
            return fisClient.getPractitionersInOrganizationByPractitionerId(practitioner);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No practitioner was found in the organization for the given practitioner");
            return null;
        }
    }

    @GetMapping("/practitioners/organization/{organizationId}")
    public PageDto<PractitionerDto> getPractitionersByOrganizationAndRole(@PathVariable("organizationId") String organization, @RequestParam(value = "role", required = false) String role) {
        try {
            return fisClient.getPractitionersByOrganizationAndRole(organization, role);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No practitioner was found for the given organization and the role (if provided) ");
            return null;
        }
    }
}
