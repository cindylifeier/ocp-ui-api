package gov.samhsa.ocp.ocpuiapi.web;

import com.netflix.discovery.converters.Auto;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.PageDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.ParticipantDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("ocp-fis")
public class ParticipantController {

    @Autowired
    private FisClient fisClient;

    @GetMapping("/participants/search")
    public PageDto<ParticipantDto> getAllParticipants(@RequestParam(value = "member") String member,
                                                      @RequestParam(value = "value") String value,
                                                      @RequestParam(value = "showInActive", defaultValue = "false") Boolean showInActive,
                                                      @RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "size", required = false) Integer size) {
        return fisClient.getAllParticipants(member, value, showInActive, page, size);
    }
}
