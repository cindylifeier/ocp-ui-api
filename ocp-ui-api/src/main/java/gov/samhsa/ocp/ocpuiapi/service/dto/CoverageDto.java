package gov.samhsa.ocp.ocpuiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoverageDto {
    private String logicalId;

    private ValueSetDto status;

    private ValueSetDto type;

    private ReferenceDto subscriber;

    private String subscriberId;

    private ReferenceDto beneficiary;

    private ValueSetDto relationship;

    private PeriodDto period;
}
