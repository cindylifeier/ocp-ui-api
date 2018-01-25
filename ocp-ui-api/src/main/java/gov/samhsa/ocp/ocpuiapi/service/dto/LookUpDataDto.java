package gov.samhsa.ocp.ocpuiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookUpDataDto {
    //In alphabetical order
    List<ValueSetDto> addressTypes;
    List<ValueSetDto> addressUses;
    List<ValueSetDto> administrativeGenders;
    List<ValueSetDto> languages;
    List<IdentifierSystemDto> locationIdentifierSystems;
    List<ValueSetDto> locationStatuses;
    List<ValueSetDto> locationTypes;
    List<ValueSetDto> locationPhysicalTypes;
    List<IdentifierSystemDto> organizationIdentifierSystems;
    List<OrganizationStatusDto> organizationStatuses;
    List<IdentifierSystemDto> patientIdentifierSystems;
    List<IdentifierSystemDto> practitionerIdentifierSystems;
    List<ValueSetDto> practitionerRoles;
    List<ValueSetDto> telecomSystems;
    List<ValueSetDto> telecomUses;
    List<ValueSetDto> usCoreBirthSex;
    List<ValueSetDto> usCoreEthnicities;
    List<ValueSetDto> usCoreRaces;
    List<ValueSetDto> uspsStates;
}
