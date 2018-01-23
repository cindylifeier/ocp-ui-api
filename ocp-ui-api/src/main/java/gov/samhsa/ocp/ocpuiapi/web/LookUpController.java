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

import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis/lookups")
public class LookUpController {

    @Autowired
    private FisClient fisClient;

    @GetMapping()
    public LookUpDataDto getAllLookUpValues(@RequestParam(value = "lookUpTypeList", required = false) List<String> lookUpTypeList) {
        LookUpDataDto lookUpData = new LookUpDataDto();

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

        if (lookUpTypeList == null || lookUpTypeList.size() == 0 || lookUpTypeList.stream().anyMatch(LookUpTypeEnum.IDENTIFIERSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpTypeEnum.IDENTIFIERSYSTEM.name());
            try {
                lookUpData.setIdentifierSystems(fisClient.getIdentifierSystems(null));
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

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
        return lookUpData;
    }
}
