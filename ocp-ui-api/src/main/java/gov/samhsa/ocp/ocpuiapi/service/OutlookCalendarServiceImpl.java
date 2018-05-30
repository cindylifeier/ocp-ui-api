package gov.samhsa.ocp.ocpuiapi.service;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.infrastructure.dto.CredentialDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.JwtTokenKey;
import gov.samhsa.ocp.ocpuiapi.service.dto.OutlookCalendarDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OutlookCalendarServiceImpl implements OutlookCalendarService {
    private final FisClient fisClient;

    private final JwtTokenExtractor jwtTokenExtractor;

    @Autowired
    public OutlookCalendarServiceImpl(FisClient fisClient, JwtTokenExtractor jwtTokenExtractor) {
        this.fisClient = fisClient;
        this.jwtTokenExtractor = jwtTokenExtractor;
    }

    @Override
    public List<OutlookCalendarDto> getOutlookCalendarAppointments() {
        String outlookEmail = (String) jwtTokenExtractor.getValueByKey(JwtTokenKey.OUTLOOK_EMAIL);
        String outlookPassword = (String) jwtTokenExtractor.getValueByKey(JwtTokenKey.OUTLOOK_PASSWORD);
        log.info("Searching for Outlook Appointments for email ID: " + outlookEmail);
        try {
            return fisClient.getOutlookCalendarAppointments(outlookEmail, outlookPassword);
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignException(fe, "that no for Outlook Appointments were found for email ID: " + outlookEmail);
            return null;
        }
    }

    @Override
    public void loginToOutlook(CredentialDto credentialDto) {
        log.info("Logging in to OWA using username: " + credentialDto.getUsername());
        try {
            fisClient.loginToOutlook(credentialDto);
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignException(fe, "that that entered username and password was not authorized.");
        }
    }
}
