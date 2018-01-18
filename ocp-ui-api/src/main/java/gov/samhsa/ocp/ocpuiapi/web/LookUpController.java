package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.ValueSetDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis/lookup")
public class LookUpController {

    @Autowired
    private FisClient fisClient;

    @GetMapping("/uspsStates")
    public List<ValueSetDto> getUspsStates() {
        log.info("Fetching State codes from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getUspsStates();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (uspsStates) were fetched from FHIR server");
            return null;
        }
    }

    /**
     * Identifies the purpose for this identifier, if known
     * Eg: Usual, Official, Temp
     * @return
     */
    @GetMapping("/identifierUses")
    public List<ValueSetDto> getIdentifierUses() {
        log.info("Fetching Identifier Uses from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getIdentifierUses();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (identifierUses) were fetched from FHIR server");
            return null;
        }
    }

    //LOCATION START
    /**
     * Determine identifier to use for a specific purpose
     * Eg: PRN , EN
     * @return
     */
    @GetMapping("/locationIdentifierTypes")
    public List<ValueSetDto> getLocationIdentifierTypes() {
        log.info("Fetching Location modes from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getLocationIdentifierTypes();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (locationIdentifierTypes) were fetched from FHIR server");
            return null;
        }
    }

    /**
     * Indicates whether a resource instance represents a specific location or a class of locations
     * Eg: INSTANCE, KIND, NULL
     * @return
     */
    @GetMapping("/locationModes")
    public List<ValueSetDto> getLocationModes() {
        log.info("Fetching Location modes from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getLocationModes();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (locationModes) were fetched from FHIR server");
            return null;
        }
    }

    /**
     * general availability of the resource
     * Eg: ACTIVE, SUSPENDED, INACTIVE, NULL
     * @return
     */
    @GetMapping("/locationStatuses")
    public List<ValueSetDto> getLocationStatuses() {
        log.info("Fetching Location statuses from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getLocationStatuses();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (locationStatuses) were fetched from FHIR server");
            return null;
        }
    }

    /**
     * Physical form of the location
     * e.g. building, room, vehicle, road.
     */
    @GetMapping("/locationTypes")
    public List<ValueSetDto> getLocationTypes() {
        log.info("Fetching Location Types from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getLocationTypes();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (locationTypes) were fetched from FHIR server");
            return null;
        }
    }

    //LOCATION END

    //ADDRESS and TELECOM START

    /**
     * The type of an address (physical / postal)
     * Eg: POSTAL, PHYSICAL, POSTAL & PHYSICAL, NULL
     * @return
     */
    @GetMapping("/addressTypes")
    public List<ValueSetDto> getAddressTypes() {
        log.info("Fetching Address Types from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getAddressTypes();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (addressTypes) were fetched from FHIR server");
            return null;
        }
    }

    /**
     * The use of an address
     * Eg: HOME, WORK, TEMP, OLD, NULL
     * @return
     */
    @GetMapping("/addressUses")
    public List<ValueSetDto> getAddressUses() {
        log.info("Fetching Address Uses from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getAddressUses();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (addressUses) were fetched from FHIR server");
            return null;
        }
    }

    /**
     * Identifies the purpose for the contact point
     * Eg: HOME, WORK, TEMP, OLD, MOBILE, NULL
     * @return
     */
    @GetMapping("/telecomUses")
    public List<ValueSetDto> getTelecomUses() {
        log.info("Fetching Telecom Uses from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getTelecomUses();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (telecomUses) were fetched from FHIR server");
            return null;
        }
    }

    /**
     * Telecommunications form for contact point - what communications system is required to make use of the contact.
     * Eg: PHONE, FAX, EMAIL, PAGER, URL, SMS, OTHER, NULL
     * @return
     */
    @GetMapping("/telecomSystems")
    public List<ValueSetDto> getTelecomSystems() {
        log.info("Fetching Telecom Types from FHIR Server...");
        try {
            List<ValueSetDto> fisClientResponse = fisClient.getTelecomSystems();
            log.info("Got response from FHIR Server...");
            return fisClientResponse;
        }catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToLookUpValues(fe, "no lookup values (telecomSystems) were fetched from FHIR server");
            return null;
        }
    }

    //ADDRESS and TELECOM END
}
