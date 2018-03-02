package gov.samhsa.ocp.ocpuiapi.web;


import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.CommunicationDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("ocp-fis")
@Slf4j
public class CommunicationController {
    @Autowired
    FisClient fisClient;

    @PostMapping("/communications")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCommunication(@Valid @RequestBody CommunicationDto communicationDto) {
        log.info("About to create a communication");
        try {
            fisClient.createCommunication(communicationDto);
            log.info("Successfully created a communication.");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, " that the communication was not created");
        }
    }

    @PutMapping("/communications/{communicationsId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCommunication(@PathVariable String communicationsId, @Valid @RequestBody CommunicationDto communicationDto){
        try{
            fisClient.updateCommunication(communicationsId,communicationDto);
            log.debug("Successfully updated a communication");
        }catch(FeignException fe){
            ExceptionUtil.handleFeignExceptionRelatedToResourceUpdate(fe,"Communication could not be updated in the FHIR server");
        }
    }


}
