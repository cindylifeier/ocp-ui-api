package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.CareTeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("ocp-fis/careteams")
public class CareTeamController {

    @Autowired
    private FisClient fisClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCareTeam(@Valid @RequestBody CareTeamDto careTeamDto) {
        fisClient.createCareTeam(careTeamDto);
    }


}