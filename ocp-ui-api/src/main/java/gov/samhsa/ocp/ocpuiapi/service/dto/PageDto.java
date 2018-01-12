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
public class PageDto<T> {
    int size;
    double totalNumberOfPages;
    int currentPage; //Starts with 1 if elements are present
    int currentPageSize; //Will always be less than or equal to "size", for example: a last page
    boolean hasNextPage;
    boolean hasPreviousPage;
    int totalElements;
    List<T> elements;
}
