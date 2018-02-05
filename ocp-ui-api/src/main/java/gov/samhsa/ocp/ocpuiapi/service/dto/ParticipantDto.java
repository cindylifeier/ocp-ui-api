package gov.samhsa.ocp.ocpuiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {

    private String roleCode;

    private String roleDisplay;

    private Optional<String> memberFirstName;

    private Optional<String> memberLastName;

    private Optional<String> memberName;

    private String memberId;

    private String memberType;

    private String onBehalfOfId;

    private String onBehalfOfName;

}
