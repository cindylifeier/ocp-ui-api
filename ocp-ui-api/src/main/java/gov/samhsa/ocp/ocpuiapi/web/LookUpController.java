package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.LoopUpDataKeyEnum;
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
@RequestMapping("ocp-fis/lookup")
public class LookUpController {

    @Autowired
    private FisClient fisClient;

    @GetMapping()
    public LookUpDataDto getAllLookups(@RequestParam(value = "lookUpKeyList", required = false) List<String> lookUpKeyList) {
        LookUpDataDto lookUpData = new LookUpDataDto();

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LoopUpDataKeyEnum.ADDRESSTYPE.name()::equalsIgnoreCase)) {
            log.info("Getting lookups for " + LoopUpDataKeyEnum.ADDRESSTYPE.name());
            try{
                lookUpData.setAddressTypes(fisClient.getAddressTypes());
            } catch (FeignException fe){
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LoopUpDataKeyEnum.ADDRESSUSE.name()::equalsIgnoreCase)) {
            log.info("Getting lookups for " + LoopUpDataKeyEnum.ADDRESSUSE.name());
            try{
                lookUpData.setAddressUses(fisClient.getAddressUses());
            } catch (FeignException fe){
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LoopUpDataKeyEnum.IDENTIFIERSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting lookups for " + LoopUpDataKeyEnum.IDENTIFIERSYSTEM.name());
            try{
                lookUpData.setIdentifierSystems(fisClient.getIdentifierSystems(null));
            } catch (FeignException fe){
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LoopUpDataKeyEnum.LOCATIONSTATUS.name()::equalsIgnoreCase)) {
            log.info("Getting lookups for " + LoopUpDataKeyEnum.LOCATIONSTATUS.name());
            try{
                lookUpData.setLocationStatuses(fisClient.getLocationStatuses());
            } catch (FeignException fe){
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LoopUpDataKeyEnum.LOCATIONTYPE.name()::equalsIgnoreCase)) {
            log.info("Getting lookups for " + LoopUpDataKeyEnum.LOCATIONTYPE.name());
            try{
                lookUpData.setLocationTypes(fisClient.getLocationTypes());
            } catch (FeignException fe){
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LoopUpDataKeyEnum.TELECOMSYSTEM.name()::equalsIgnoreCase)) {
            log.info("Getting lookups for " + LoopUpDataKeyEnum.TELECOMSYSTEM.name());
            try{
                lookUpData.setTelecomSystems(fisClient.getTelecomSystems());
            } catch (FeignException fe){
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LoopUpDataKeyEnum.TELECOMUSE.name()::equalsIgnoreCase)) {
            log.info("Getting lookups for " + LoopUpDataKeyEnum.TELECOMUSE.name());
            try{
                lookUpData.setTelecomUses(fisClient.getTelecomUses());
            } catch (FeignException fe){
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }
        }

        if (lookUpKeyList == null || lookUpKeyList.size() == 0 || lookUpKeyList.stream().anyMatch(LoopUpDataKeyEnum.USPSSTATES.name()::equalsIgnoreCase)) {
            log.info("Getting lookups for " + LoopUpDataKeyEnum.USPSSTATES.name());
            try{
                lookUpData.setUspsStates(fisClient.getUspsStates());
            } catch (FeignException fe){
                //Do nothing
                log.error("Caution: No look up values found. Please check ocp-fis logs for error details.");
            }

        }
        return lookUpData;
    }

}
