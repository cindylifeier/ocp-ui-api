package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupRequestDto;

public interface UaaGroupService {

    public Object getAllGroups();

    public Object getAllScopes();

    public Object getAllUsers();

    public Object getAllUserInfos();

    public Object getAllUsersByOrganization();

    public void createGroup(GroupRequestDto groupRequestDto);

}
