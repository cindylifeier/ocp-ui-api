package gov.samhsa.ocp.ocpuiapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OcpUiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcpUiApiApplication.class, args);
    }
}
