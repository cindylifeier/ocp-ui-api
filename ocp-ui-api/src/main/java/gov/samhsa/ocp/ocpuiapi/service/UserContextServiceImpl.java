package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.JwtTokenKey;
import gov.samhsa.ocp.ocpuiapi.service.dto.OrganizationDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PatientDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.PractitionerDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.UserContextDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserContextServiceImpl implements UserContextService{

    @Autowired
    FisClient fisClient;

    @Autowired
    JwtTokenExtractor jwtTokenExtractor;

    enum UserType {
        PRACTITIONER, PATIENT, OCPADMIN
    }

    @Override
    public String getUserOrganizationId() {
        Map extAttr = (Map) jwtTokenExtractor.getValueByKey(JwtTokenKey.EXT_ATTR);
        return extAttr.get("orgId").toString();
    }

    @Override
    public UserType getUserResourceType() {
        Map extAttr = (Map) jwtTokenExtractor.getValueByKey(JwtTokenKey.EXT_ATTR);
        if(extAttr.get("resource").toString().equalsIgnoreCase("Practitioner"))
            return UserType.PRACTITIONER;
        if(extAttr.get("resource").toString().equalsIgnoreCase("Patient"))
            return UserType.PATIENT;
        if(extAttr.get("resource").toString().equalsIgnoreCase("ocpAdmin"))
            return UserType.OCPADMIN;
        return null;
    }

    @Override
    public String getUserResourceId() {
        Map extAttr = (Map) jwtTokenExtractor.getValueByKey(JwtTokenKey.EXT_ATTR);
        return extAttr.get("id").toString();
    }

    @Override
    public UserContextDto getUserContext() {
        Object fhirResource = null;
        UserType resourceType = getUserResourceType();
        if(resourceType.equals(UserType.PRACTITIONER)){
            fhirResource = fisClient.getPractitioner(getUserResourceId());
        }
        if(resourceType.equals(UserType.PATIENT)){
            fhirResource = fisClient.getPatientById(getUserResourceId());
        }
        OrganizationDto organization = fisClient.getOrganization(getUserOrganizationId());
        return UserContextDto.builder().organization(organization).fhirResource(fhirResource).build();
    }

    @Override
    public String getUserFhirId() {
        String fhirId = "";
        UserType resourceType = getUserResourceType();
        if(resourceType.equals(UserType.PRACTITIONER)){
            fhirId = "Practitioner/" + getUserResourceId();
        }

        if(resourceType.equals(UserType.PATIENT)) {
            fhirId = "Patient/" + getUserResourceId();
        }

        if(resourceType.equals(UserType.OCPADMIN)) {
            //TODO: Id has to be a valid fhir id. Currently ocpAdmin does not have a valid fhir id
            fhirId = "Practitioner/323";
        }

        return fhirId;
    }
}
