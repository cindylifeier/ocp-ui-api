package gov.samhsa.ocp.ocpuiapi.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class SecurityConfig {

    private static final String RESOURCE_ID = "ocpUiApi";

    @Bean
    public ResourceServerConfigurer resourceServer(SecurityProperties securityProperties) {
        return new ResourceServerConfigurerAdapter() {
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) {
                resources.resourceId(RESOURCE_ID);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                if (securityProperties.isRequireSsl()) {
                    http.requiresChannel().anyRequest().requiresSecure();
                }
                http.authorizeRequests()
                        .antMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/lookups/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/user-context").access("#oauth2.hasScopeMatching('ocp.role.*')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/organizations/*/activity-definitions/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/organizations/*/activity-definitions/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/activity-definitions/**").permitAll()
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/organizations/*/activity-definitions/**").permitAll()
                        //.antMatchers(HttpMethod.PUT, "/ocp-fis/activity-definitions/**").access("#oauth2.hasScopeMatching('ocpUiApi.activityDefinition_update')")
                        //.antMatchers(HttpMethod.DELETE, "/ocp-fis/activity-definitions/**").access("#oauth2.hasScopeMatching('ocpUiApi.activityDefinition_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/coverage/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/patients/*/subscriber-options/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/patients/*/coverages/**").permitAll()

                        .antMatchers(HttpMethod.POST, "/ocp-fis/appointments/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/appointments/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/ocp-fis/outlook/login/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/outlook/calendar/**").permitAll()
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/appointments/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/appointments/**").permitAll()

                        .antMatchers(HttpMethod.POST, "/ocp-fis/care-teams/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/care-teams/**").permitAll()
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/care-teams/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/care-teams/**").permitAll()

                        .antMatchers(HttpMethod.POST, "/ocp-fis/communications/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/communications/**").permitAll()
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/communications/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/communications/**").permitAll()

                        .antMatchers(HttpMethod.POST, "/ocp-fis/consents/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/consents/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/consents/generalConsent/").permitAll()
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/consents/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/consents/**").permitAll()
                        .antMatchers(HttpMethod.GET,"/ocp-fis/actors/**").permitAll()

                        .antMatchers(HttpMethod.POST, "/ocp-fis/organization/*/healthcare-services/**").access("#oauth2.hasScopeMatching('ocpUiApi.healthcareService_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/healthcare-services/**").access("#oauth2.hasScopeMatching('ocpUiApi.healthcareService_read')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/organizations/*/healthcare-services/**").access("#oauth2.hasScopeMatching('ocpUiApi.healthcareService_read')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/organizations/*/locations/*/healthcare-services/**").access("#oauth2.hasScopeMatching('ocpUiApi.healthcareService_read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/healthcare-services/*/assign").access("#oauth2.hasScopeMatching('ocpUiApi.healthcareService_assign')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/healthcare-services/*/unassign").access("#oauth2.hasScopeMatching('ocpUiApi.healthcareService_unassign')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/organization/*/healthcare-services/**").access("#oauth2.hasScopeMatching('ocpUiApi.healthcareService_update')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/healthcare-services/*/inactive/**").access("#oauth2.hasScopeMatching('ocpUiApi.healthcareService_update')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/healthcare-services/**").access("#oauth2.hasScopeMatching('ocpUiApi.healthcareService_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/organizations/*/locations/**").access("#oauth2.hasScopeMatching('ocpUiApi.location_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/locations/**").access("#oauth2.hasScopeMatching('ocpUiApi.location_read')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/organizations/*/locations/**").access("#oauth2.hasScopeMatching('ocpUiApi.location_read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/organizations/*/locations/**").access("#oauth2.hasScopeMatching('ocpUiApi.location_update')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/locations/**").access("#oauth2.hasScopeMatching('ocpUiApi.location_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/organizations/**").access("#oauth2.hasScopeMatching('ocpUiApi.organization_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/organizations/**").access("#oauth2.hasScopeMatching('ocpUiApi.organization_read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/organizations/**").access("#oauth2.hasScopeMatching('ocpUiApi.organization_update')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/organizations/**").access("#oauth2.hasScopeMatching('ocpUiApi.organization_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/patients/**").access("#oauth2.hasScopeMatching('ocpUiApi.patient_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/patients/**").access("#oauth2.hasScopeMatching('ocpUiApi.patient_read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/patients/**").access("#oauth2.hasScopeMatching('ocpUiApi.patient_update')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/patients/**").access("#oauth2.hasScopeMatching('ocpUiApi.patient_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/practitioners/**").access("#oauth2.hasScopeMatching('ocpUiApi.practitioner_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/practitioners/**").access("#oauth2.hasScopeMatching('ocpUiApi.practitioner_read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/practitioners/**").access("#oauth2.hasScopeMatching('ocpUiApi.practitioner_update')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/practitioners/**").access("#oauth2.hasScopeMatching('ocpUiApi.practitioner_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/related-persons/**").access("#oauth2.hasScopeMatching('ocpUiApi.relatedPerson_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/related-persons/**").access("#oauth2.hasScopeMatching('ocpUiApi.relatedPerson_read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/related-persons/**").access("#oauth2.hasScopeMatching('ocpUiApi.relatedPerson_update')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/related-persons/**").access("#oauth2.hasScopeMatching('ocpUiApi.relatedPerson_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/tasks/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/tasks/**").permitAll()
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/tasks/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/tasks/**").permitAll()

                        //TODO: Secure Participant, EpisodeOfCare APIs
                        .antMatchers(HttpMethod.GET, "/ocp-fis/participants/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/episode-of-cares/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/config").permitAll()
                        .antMatchers(HttpMethod.GET, "/user-context").access("#oauth2.hasScopeMatching('ocp.role.*')")
                        .antMatchers(HttpMethod.GET, "/smart/app-shortcuts").permitAll()
                        .antMatchers(HttpMethod.POST, "/smart/**").access("#oauth2.hasScopeMatching('ocp.role.smartUser')")
                        .anyRequest().denyAll();
            }
        };
    }
}
