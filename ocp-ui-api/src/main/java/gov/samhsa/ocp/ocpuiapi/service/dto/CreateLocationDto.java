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
public class CreateLocationDto {
    private String status;
    private String name;
    private String physicalType;

    private List<IdentifierDto> identifiers;
    private AddressDto address;
    private List<TelecomDto> telecoms;
}
