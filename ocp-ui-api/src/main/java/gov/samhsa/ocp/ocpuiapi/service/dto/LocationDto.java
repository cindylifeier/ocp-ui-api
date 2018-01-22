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
public class LocationDto {
    private String logicalId;
    private String resourceURL;

    private String status;
    private String name;
    private String physicalType;
    private AddressDto address;
    private List<TelecomDto> telecoms;
    private List<IdentifierDto> identifiers;
}
