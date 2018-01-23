package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ResourceType;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis")

public class OrganizationController {


    public enum SearchType {
        identifier, name, logicalId
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
    public PageDto<OrganizationDto> getAllOrganizations(@RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                     @RequestParam(value = "page", required = false) Integer page,
                                                     @RequestParam(value = "size", required = false) Integer size) {
        log.info("Fetching organizations from FHIR server");
        try {
            PageDto<OrganizationDto> organizations = fisClient.getAllOrganizations(showInactive, page, size);
            log.info("Got response from FHIR server for all organizations");
            return organizations;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no organizations were found in the configured FHIR server", ResourceType.ORGANIZATION.name());
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
    public PageDto<OrganizationDto> searchOrganizations(@RequestParam(value = "searchType", required = false) String searchType,
                                                        @RequestParam(value = "searchValue", required = false) String searchValue,
                                                        @RequestParam(value = "showInactive", required = false) boolean showInactive,
                                                        @RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "size", required = false) Integer size) {
        log.info("Searching organizations from FHIR server");
        try {
            PageDto<OrganizationDto> organizations = fisClient.searchOrganizations(searchType, searchValue, showInactive, page, size);
            log.info("Got response from FHIR server for organization search");
            return organizations;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "no organizations were found found in the configured FHIR server for the given searchType and searchValue",  ResourceType.ORGANIZATION.name());
            return null;
        }
    }


    @PostMapping("/organizations")
    @ResponseStatus(HttpStatus.CREATED)
    void createOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
        log.info("About to create a organization");
        try {
            fisClient.createOrganization(organizationDto);
            log.info("Successfully created the organization");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, " that the organization was not created", ResourceType.ORGANIZATION.name());
        }
    }


}
