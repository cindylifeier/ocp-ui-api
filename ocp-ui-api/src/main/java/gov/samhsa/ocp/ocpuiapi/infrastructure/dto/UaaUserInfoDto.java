package gov.samhsa.ocp.ocpuiapi.infrastructure.dto;

import lombok.Data;

@Data
public class UaaUserInfoDto {
    private String user_id;
    private String resource;
    private String id;
    private String orgId;
}
