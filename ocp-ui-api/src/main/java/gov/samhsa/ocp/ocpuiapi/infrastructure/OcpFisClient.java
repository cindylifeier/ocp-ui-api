package gov.samhsa.ocp.ocpuiapi.infrastructure;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name ="ocp-fis", url = "${ribbon.listOfServers}")
public interface OcpFisClient {

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    Object getAllLocations(@RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/organizations/{organizationId}/locations", method = RequestMethod.GET)
    Object getLocationsByOrganization(@PathVariable("organizationId") String organizationId,
                                      @RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/locations/{locationId}", method = RequestMethod.GET)
    Object getLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/locations/{locationId}/childLocation", method = RequestMethod.GET)
    Object getChildLocation(@PathVariable("locationId") String locationId);

    @RequestMapping(value = "/practitioners", method = RequestMethod.GET)
    Object getAllPractitioners(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size);
}
