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
    List<IdentifierSystemDto> locationIdentifierSystems;
    List<ValueSetDto> locationStatuses;
    List<ValueSetDto> locationTypes;
    List<OrganizationStatusDto> organizationStatuses;
    List<ValueSetDto> locationPhysicalTypes;
    List<IdentifierSystemDto> organizationIdentifierSystems;
    List<IdentifierSystemDto> patientIdentifierSystems;
    List<IdentifierSystemDto> practitionerIdentifierSystems;
    List<ValueSetDto> telecomSystems;
    List<ValueSetDto> telecomUses;
    List<ValueSetDto> uspsStates;
}
