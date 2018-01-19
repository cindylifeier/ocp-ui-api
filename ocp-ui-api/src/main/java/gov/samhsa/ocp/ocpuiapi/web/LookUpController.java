package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.LookUpKeyEnum;
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
    public LookUpDataDto getAllLookUpValues(@RequestParam(value = "lookUpKeyList", required = false) List<String> lookUpKeyList) {
        LookUpDataDto lookUpData = new LookUpDataDto();

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LookUpKeyEnum.ADDRESSTYPE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpKeyEnum.ADDRESSTYPE.name());
            try {
                lookUpData.setAddressTypes(fisClient.getAddressTypes());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LookUpKeyEnum.ADDRESSUSE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpKeyEnum.ADDRESSUSE.name());
            try {
                lookUpData.setAddressUses(fisClient.getAddressUses());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LookUpKeyEnum.IDENTIFIERSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpKeyEnum.IDENTIFIERSYSTEM.name());
            try {
                lookUpData.setIdentifierSystems(fisClient.getIdentifierSystems(null));
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LookUpKeyEnum.LOCATIONSTATUS.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpKeyEnum.LOCATIONSTATUS.name());
            try {
                lookUpData.setLocationStatuses(fisClient.getLocationStatuses());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LookUpKeyEnum.LOCATIONTYPE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpKeyEnum.LOCATIONTYPE.name());
            try {
                lookUpData.setLocationTypes(fisClient.getLocationTypes());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LookUpKeyEnum.TELECOMSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpKeyEnum.TELECOMSYSTEM.name());
            try {
                lookUpData.setTelecomSystems(fisClient.getTelecomSystems());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LookUpKeyEnum.TELECOMUSE.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpKeyEnum.TELECOMUSE.name());
            try {
                lookUpData.setTelecomUses(fisClient.getTelecomUses());
            }
            catch (FeignException fe) {
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LookUpKeyEnum.USPSSTATES.name()::equalsIgnoreCase)) {
            log.info("Getting look up values for " + LookUpKeyEnum.USPSSTATES.name());
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
