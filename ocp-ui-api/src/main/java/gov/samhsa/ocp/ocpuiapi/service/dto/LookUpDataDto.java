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
    List<ValueSetDto> healthcareServiceCategories;
    List<ValueSetDto> healthcareServiceTypes;
    List<ValueSetDto> healthcareServiceSpecialities;
    List<ValueSetDto> healthcareServiceReferralMethods;
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
    List<ValueSetDto> careTeamCategories;
    List<ValueSetDto> participantTypes;
    List<ValueSetDto> careTeamStatuses;
    List<ValueSetDto> participantRoles;
    List<ValueSetDto> careTeamReasons;
    List<ValueSetDto> publicationStatus;
    List<ValueSetDto> definitionTopic;
    List<ValueSetDto> resourceType;
    List<ValueSetDto> actionParticipantType;
    List<ValueSetDto> actionParticipantRole;
    List<ValueSetDto> relatedPersonPatientRelationshipTypes;
    List<ValueSetDto> taskStatus;
    List<ValueSetDto> requestPriority;
    List<ValueSetDto> taskPerformerType;
    List<ValueSetDto> requestIntent;
}
