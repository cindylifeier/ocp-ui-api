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
public class ParticipantSearchDto {
    //private ValueSetDto role;

    private ParticipantMemberDto member;

    private ParticipantOnBehalfOfDto onBehalfOfDto;

    //private String uaaRole;

    private List<TelecomDto> telecoms;

    private List<AddressDto> addresses;

    private List<PractitionerRoleDto> practitionerRoles;
}
