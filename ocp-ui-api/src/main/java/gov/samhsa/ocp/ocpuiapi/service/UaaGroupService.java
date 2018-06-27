package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupRequestDto;

public interface UaaGroupService {

    public Object getAllGroups();

    public Object getAllScopes();

    public Object getAllUsersByOrganizationId(String organizationId);

    public void createGroup(GroupRequestDto groupRequestDto);

    public void updateGroup(String groupId, GroupRequestDto groupRequestDto);

}
