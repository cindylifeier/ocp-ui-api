package gov.samhsa.ocp.ocpuiapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDefinitionDto {
    private String logicalId;
    private String version;
    private String name;
    private String title;
    private ValueSetDto status;

    private LocalDate date;
    private String publisherReference;
    private String description;

    private PeriodDto effectivePeriod;
    private ValueSetDto topic;
    private List<ValueSetDto> relatedArtifact;
    private ValueSetDto kind;

    private TimingDto timing;
    private ActionParticipantDto participant;
}
