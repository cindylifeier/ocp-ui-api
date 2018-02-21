package gov.samhsa.ocp.ocpuiapi.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String logicalId;

    private ReferenceDto definition;

    private ReferenceDto partOf;

    private ValueSetDto status;

    private ValueSetDto intent;

    private ValueSetDto priority;

    private String description;

    private ReferenceDto beneficiary;

    private ContextDto context;

    private PeriodDto executionPeriod;

    private LocalDate authoredOn;

    private LocalDate lastModified;

    private ReferenceDto agent;

    private ReferenceDto onBehalfOf;

    private ValueSetDto performerType;

    private ReferenceDto owner;

    private String note;
}