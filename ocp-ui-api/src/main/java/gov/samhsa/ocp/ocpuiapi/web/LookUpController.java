package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.LookUpTypeEnum;
import gov.samhsa.ocp.ocpuiapi.service.dto.LookUpDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis/lookups")
public class LookUpController {

    @Autowired
    private FisClient fisClient;

    final List<String> allowedlocationIdentifierTypes = Arrays.asList("EN", "TAX", "NIIP", "PRN");
    final List<String> allowedOrganizationIdentifierTypes = Arrays.asList("EN", "TAX", "NIIP", "PRN");
    final List<String> allowedPatientIdentifierTypes = Arrays.asList("DL", "PPN", "TAX", "MR","DR","SB");
    final List<String> allowedPractitionerIdentifierTypes = Arrays.asList("PRN", "TAX", "MD", "SB");

    @GetMapping()
    public LookUpDataDto getAllLookUpValues(@RequestParam(value = "lookUpTypeList", required = false) List<String> lookUpTypeList) {
        LookUpDataDto lookUpData = new LookUpDataDto();

        //Adddress type
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.ADDRESSTYPE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.ADDRESSTYPE.name());
            try {
                lookUpData.setAddressTypes(fisClient.getAddressTypes());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        //Address use
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.ADDRESSUSE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.ADDRESSUSE.name());
            try {
                lookUpData.setAddressUses(fisClient.getAddressUses());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        //Location identifier system
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.LOCATIONIDENTIFIERSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.LOCATIONIDENTIFIERSYSTEM.name());
            try {
                lookUpData.setLocationIdentifierSystems(fisClient.getIdentifierSystems(allowedlocationIdentifierTypes));
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        //Location status
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.LOCATIONSTATUS.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.LOCATIONSTATUS.name());
            try {
                lookUpData.setLocationStatuses(fisClient.getLocationStatuses());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        //Location Physical Type
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.LOCATIONPHYSICALTYPE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.LOCATIONPHYSICALTYPE.name());
            try {
                lookUpData.setLocationPhysicalTypes(fisClient.getLocationPhysicalTypes());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        //Org identifier system
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.ORGANIZATIONIDENTIFIERSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.ORGANIZATIONIDENTIFIERSYSTEM.name());
            try {
                lookUpData.setOrganizationIdentifierSystems(fisClient.getIdentifierSystems(allowedOrganizationIdentifierTypes));
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.ORGANIZATIONSTATUS.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.ORGANIZATIONSTATUS.name());
            try {
                lookUpData.setOrganizationStatuses(fisClient.getOrganizationStatuses());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }

        }

        //Patient identifier system
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.PATIENTIDENTIFIERSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.PATIENTIDENTIFIERSYSTEM.name());
            try {
                lookUpData.setPatientIdentifierSystems(fisClient.getIdentifierSystems(allowedPatientIdentifierTypes));
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        //Practitioner identifier system
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.PRACTITIONERIDENTIFIERSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.PRACTITIONERIDENTIFIERSYSTEM.name());
            try {
                lookUpData.setPractitionerIdentifierSystems(fisClient.getIdentifierSystems(allowedPractitionerIdentifierTypes));
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        //Telecom Systems
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.TELECOMSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.TELECOMSYSTEM.name());
            try {
                lookUpData.setTelecomSystems(fisClient.getTelecomSystems());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        //Telecom Use
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.TELECOMUSE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.TELECOMUSE.name());
            try {
                lookUpData.setTelecomUses(fisClient.getTelecomUses());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        //US States
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.USPSSTATES.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.USPSSTATES.name());
            try {
                lookUpData.setUspsStates(fisClient.getUspsStates());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }

        }

        //Administrative Gender
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.ADMINISTRATIVEGENDER.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.ADMINISTRATIVEGENDER.name());
            try {
                lookUpData.setAdministrativeGenders(fisClient.getAdministrativeGenders());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }

        }

        //US Core Races
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.USCORERACE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.USCORERACE.name());
            try {
                lookUpData.setUsCoreRaces(fisClient.getUSCoreRaces());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }

        }

        //US Core Ethnicities
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.USCOREETHNICITY.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.USCOREETHNICITY.name());
            try {
                lookUpData.setUsCoreEthnicities(fisClient.getUSCoreEthnicities());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }

        }

        //US Core Birthsexes
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.USCOREBIRTHSEX.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.USCOREBIRTHSEX.name());
            try {
                lookUpData.setUsCoreBirthSex(fisClient.getUSCoreBirthsexes());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }

        }

        //Languages
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.LANGUAGE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.LANGUAGE.name());
            try {
                lookUpData.setLanguages(fisClient.getLanguages());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }

        }


        return lookUpData;
    }
}
