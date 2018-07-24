package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.service.dto.UserLoginDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@Slf4j
public class UserLoginDetailsServiceImpl implements UserLoginDetailsService {

    private static final String SAMPLE_USER_LOGIN_DETAILS_PDF = "sample-user-login-details.pdf";

    @Override
    public UserLoginDetailsDto getUserLoginDetails() {
        UserLoginDetailsDto userLoginDetailsDto = new UserLoginDetailsDto();
        try {
            File file = new ClassPathResource(SAMPLE_USER_LOGIN_DETAILS_PDF).getFile();
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            String encodedBase64 = new String(Base64.encodeBase64(bytes));
            userLoginDetailsDto.setEncodedPdf(encodedBase64);
        } catch (IOException e) {
            log.info("No source pdf file found." + e);
        }
        return userLoginDetailsDto;
    }
}
