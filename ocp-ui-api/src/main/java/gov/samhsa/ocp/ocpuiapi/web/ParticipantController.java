package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantSearchDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ReferenceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("ocp-fis/participants")
public class ParticipantController {

    @Autowired
    private FisClient fisClient;

    @GetMapping("/search")
    public PageDto<ParticipantSearchDto> getAllParticipants(@RequestParam(value = "patientId") String patientId,
                                                            @RequestParam(value = "member") String member,
                                                            @RequestParam(value = "value") String value,
                                                            @RequestParam(value = "showInActive", defaultValue = "false") Boolean showInActive,
                                                            @RequestParam(value = "page", required = false) Integer page,
                                                            @RequestParam(value = "size", required = false) Integer size) {
        return fisClient.getAllParticipants(patientId, member, value, showInActive, page, size);
    }

    @GetMapping
    List<ReferenceDto> getCareTeamParticipants(@RequestParam(value = "patient") String patient,
                                               @RequestParam(value = "roles") List<String> roles) {
        return fisClient.getCareTeamParticipants(patient, roles);
    }
}
