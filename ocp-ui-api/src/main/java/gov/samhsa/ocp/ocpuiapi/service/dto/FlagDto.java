package gov.samhsa.ocp.ocpuiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlagDto {
    private String logicalId;

    private ValueSetDto status;

    private ValueSetDto category;

    private String code;

    private String subject;

    private PeriodDto period;

    private ReferenceDto author;
}
