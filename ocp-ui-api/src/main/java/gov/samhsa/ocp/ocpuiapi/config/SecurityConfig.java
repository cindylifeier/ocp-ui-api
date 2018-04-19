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

                        .antMatchers(HttpMethod.POST, "/ocp-fis/organizations/*/activity-definitions").access("#oauth2.hasScopeMatching('ocpUiApi.activityDefinition_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/organizations/*/activity-definitions").access("#oauth2.hasScopeMatching('ocpUiApi.activityDefinition_read')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/activity-definitions").access("#oauth2.hasScopeMatching('ocpUiApi.activityDefinition_read')")
                        //.antMatchers(HttpMethod.PUT, "/ocp-fis/**").access("#oauth2.hasScopeMatching('ocpUiApi.activityDefinition_update')")
                        //.antMatchers(HttpMethod.DELETE, "/ocp-fis/**").access("#oauth2.hasScopeMatching('ocpUiApi.activityDefinition_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/appointments/**").access("#oauth2.hasScopeMatching('ocpUiApi.appointment_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/appointments/**").access("#oauth2.hasScopeMatching('ocpUiApi.appointment_read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/appointments/**").access("#oauth2.hasScopeMatching('ocpUiApi.appointment_update')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/appointments/**").access("#oauth2.hasScopeMatching('ocpUiApi.appointment_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/care-teams/**").access("#oauth2.hasScopeMatching('ocpUiApi.careTeam_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/care-teams/**").access("#oauth2.hasScopeMatching('ocpUiApi.careTeam_read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/care-teams/**").access("#oauth2.hasScopeMatching('ocpUiApi.careTeam_update')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/care-teams/**").access("#oauth2.hasScopeMatching('ocpUiApi.careTeam_delete')")

                        .antMatchers(HttpMethod.POST, "/ocp-fis/communications/**").access("#oauth2.hasScopeMatching('ocpUiApi.communication_create')")
                        .antMatchers(HttpMethod.GET, "/ocp-fis/communications/**").access("#oauth2.hasScopeMatching('ocpUiApi.communication_read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/communications/**").access("#oauth2.hasScopeMatching('ocpUiApi.communication_update')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/communications/**").access("#oauth2.hasScopeMatching('ocpUiApi.communication_delete')")

                        .antMatchers(HttpMethod.GET, "/user-context").access("#oauth2.hasScopeMatching('ocp.role.*')")
                        .anyRequest().denyAll();
            }
        };
    }
}
