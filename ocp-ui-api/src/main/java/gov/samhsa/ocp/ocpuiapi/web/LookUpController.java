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

    public static final String NO_LOOKUPS_FOUND_MESSAGE = "Caution!!: No look up values found. Please check ocp-fis logs for error details.";
    final List<String> allowedLocationIdentifierTypes = Arrays.asList("EN", "TAX", "NIIP", "PRN");
    final List<String> allowedOrganizationIdentifierTypes = Arrays.asList("EN", "TAX", "NIIP", "PRN");
    final List<String> allowedPatientIdentifierTypes = Arrays.asList("DL", "PPN", "TAX", "MR", "DR", "SB");
    final List<String> allowedPractitionerIdentifierTypes = Arrays.asList("PRN", "TAX", "MD", "SB");
    @Autowired
    private FisClient fisClient;

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
                log.error("(" + LookUpTypeEnum.ADDRESSTYPE.name() + ")" + NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.ADDRESSUSE.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        //Location identifier system
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.LOCATIONIDENTIFIERSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.LOCATIONIDENTIFIERSYSTEM.name());
            try {
                lookUpData.setLocationIdentifierSystems(fisClient.getIdentifierSystems(allowedLocationIdentifierTypes));
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("(" + LookUpTypeEnum.LOCATIONIDENTIFIERSYSTEM.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.LOCATIONSTATUS.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.LOCATIONPHYSICALTYPE.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.ORGANIZATIONIDENTIFIERSYSTEM.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.ORGANIZATIONSTATUS.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.ORGANIZATIONSTATUS.name());
            try {
                lookUpData.setOrganizationStatuses(fisClient.getOrganizationStatuses());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("(" + LookUpTypeEnum.ORGANIZATIONSTATUS.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.PATIENTIDENTIFIERSYSTEM.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.PRACTITIONERIDENTIFIERSYSTEM.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.TELECOMSYSTEM.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.TELECOMUSE.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.USPSSTATES.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.PRACTITIONERROLES.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.PRACTITIONERROLES.name());
            try {
                lookUpData.setPractitionerRoles(fisClient.getPractitionerRoles());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("(" + LookUpTypeEnum.PRACTITIONERROLES.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.ADMINISTRATIVEGENDER.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.USCORERACE.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.USCOREETHNICITY.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.USCOREBIRTHSEX.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
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
                log.error("(" + LookUpTypeEnum.LANGUAGE.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        //Healthcare service categories
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.HEALTHCARESERVICECATEGORY.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.HEALTHCARESERVICECATEGORY.name());
            try {
                lookUpData.setHealthcareServiceCategories(fisClient.getHealthcareServiceCategories());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("(" + LookUpTypeEnum.HEALTHCARESERVICECATEGORY.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        //Healthcare service types
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.HEALTHCARESERVICETYPE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.HEALTHCARESERVICETYPE.name());
            try {
                lookUpData.setHealthcareServiceTypes(fisClient.getHealthcareServiceTypes());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("(" + LookUpTypeEnum.HEALTHCARESERVICETYPE.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        //Healthcare service specialities
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.HEALTHCARESERVICESPECIALITY.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.HEALTHCARESERVICESPECIALITY.name());
            try {
                lookUpData.setHealthcareServiceSpecialities(fisClient.getHealthcareServiceSpecialities());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("(" + LookUpTypeEnum.HEALTHCARESERVICESPECIALITY.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        //Healthcare service ReferralMethods
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.HEALTHCARESERVICEREFERRALMETHOD.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.HEALTHCARESERVICEREFERRALMETHOD.name());
            try {
                lookUpData.setHealthcareServiceReferralMethods(fisClient.getHealthcareServiceReferralMethods());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("(" + LookUpTypeEnum.HEALTHCARESERVICEREFERRALMETHOD.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }


        //Care Team Category
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.CARETEAMCATEGORY.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.CARETEAMCATEGORY.name());
            try {
                lookUpData.setCareTeamCategories(fisClient.getCareTeamCategories());
            }
            catch (FeignException fe) {
                log.error("(" + LookUpTypeEnum.CARETEAMCATEGORY.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        //Participant Type
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.PARTICIPANTTYPE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.PARTICIPANTTYPE.name());
            try {
                lookUpData.setParticipantTypes(fisClient.getParticipantTypes());
            }
            catch (FeignException fe) {
                log.error("(" + LookUpTypeEnum.PARTICIPANTTYPE.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        //Care Team Status
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.CARETEAMSTATUS.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.CARETEAMSTATUS.name());
            try {
                lookUpData.setCareTeamStatuses(fisClient.getCareTeamStatuses());
            }
            catch (FeignException fe) {
                log.error("(" + LookUpTypeEnum.CARETEAMSTATUS.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        //Participant Role
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.PARTICIPANTROLE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.PARTICIPANTROLE.name());
            try {
                lookUpData.setParticipantRoles(fisClient.getParticipantRoles());
            }
            catch (FeignException fe) {
                log.error("(" + LookUpTypeEnum.PARTICIPANTROLE.name() + ")" +NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        //Care Team Reason
        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.CARETEAMREASON.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.CARETEAMREASON.name());
            try {
                lookUpData.setCareTeamReasons(fisClient.getCareTeamReasons());
            }
            catch (FeignException fe) {
                log.error("(" + LookUpTypeEnum.CARETEAMREASON.name() + ")" + NO_LOOKUPS_FOUND_MESSAGE);
            }
        }

        return lookUpData;
    }
}
