package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis")

public class OrganizationController {


    public enum SearchType {
        identifier, name
    }

    @Autowired
    private FisClient fisClient;

    /**
     * Example: http://localhost:8446/ocp-fis/organizations/
     * http://localhost:8446/ocp-fis/organizations?showInActive=true&page=1&size=10
     * @param showInactive
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/organizations")
    public List<OrganizationDto> getAllOrganizations(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                     @RequestParam(value = "page", required = false) Integer page,
                                                     @RequestParam(value = "size", required = false) Integer size) {
        log.info("Fetching organizations from FHIR server");
        try {
            List<OrganizationDto> organizations = fisClient.getAllOrganizations(showInactive, page, size);
            log.info("Got response from FHIR server for all organizations");
            return organizations;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToOrganizationSearch(fe, "no organizations were found in the configured FHIR server");
            return null;
        }
    }

    /**
     * Example: http://localhost:8446/ocp-fis/organizations/search?searchType=name&searchValue=smith&showInactive=true&page=1&size=10
     * @param searchType
     * @param searchValue
     * @param showInactive
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/organizations/search")
    public List<OrganizationDto> searchOrganizations(@RequestParam(value = "searchType", required = false) OrganizationController.SearchType searchType,
                                                     @RequestParam(value = "searchValue", required = false) String searchValue,
                                                     @RequestParam(value = "showInactive", required = false) Boolean showInactive,
                                                     @RequestParam(value = "page", required = false) Integer page,
                                                     @RequestParam(value = "size", required = false) Integer size) {
        log.info("Searching organizations from FHIR server");
        try {
            List<OrganizationDto> organizations = fisClient.searchOrganizations(searchType, searchValue, showInactive, page, size);
            log.info("Got response from FHIR server for organization search");
            return organizations;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToOrganizationSearch(fe, "no organizations were found found in the configured FHIR server for the given searchType and searchValue");
            return null;
        }
    }

}
