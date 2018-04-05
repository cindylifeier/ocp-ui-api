package gov.samhsa.ocp.ocpuiapi.service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ConsentDto {

    private String logicalId;

    private IdentifierDto identifier;

    private PeriodDto period;

    private LocalDateTime dateTime;

    private ValueSetDto status;

    private boolean generalDesignation;

    private ReferenceDto patient;

    private List<ReferenceDto> fromActor;

    private List<ReferenceDto> toActor;

    private List<ValueSetDto> category;

    private List<ValueSetDto> purpose;

}