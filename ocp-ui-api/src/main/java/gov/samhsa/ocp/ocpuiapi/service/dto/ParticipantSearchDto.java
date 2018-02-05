package gov.samhsa.ocp.ocpuiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantSearchDto {
    private ValueSetDto role;

    private ParticipantMemberDto member;

    private ParticipantOnBehalfOfDto onBehalfOfDto;
}
