package gov.samhsa.ocp.ocpuiapi.web;

import gov.samhsa.ocp.ocpuiapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ocp-fis")
public class OcpFisController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public Object getAllLocations(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "size", required = false) Integer size) {
        return locationService.getAllLocations(page, size);
    }

    @GetMapping("/organizations/{organizationId}/locations")
    public Object getLocationsByOrganization(@PathVariable String organizationId,
                                             @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "size", required = false) Integer size) {
        return locationService.getLocationsByOrganization(organizationId, page, size);
    }

    @GetMapping("/locations/{locationId}")
    public Object getLocation(@PathVariable String locationId) {
        return locationService.getLocation(locationId);
    }

    @GetMapping("/locations/{locationId}/childLocation")
    public Object getChildLocation(@PathVariable String locationId) {
        return locationService.getChildLocation(locationId);
    }
}
