package gov.samhsa.ocp.ocpuiapi.service;

import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.RoleToUserDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.group.GroupRequestDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.uaa.user.UserDto;

public interface UaaGroupService {

    public Object getAllGroups();

    public Object getAllScopes();

    public Object getAllUsersByOrganizationId(String organizationId, String resource);

    public void createGroup(GroupRequestDto groupRequestDto);

    public void updateGroup(String groupId, GroupRequestDto groupRequestDto);

    public void assignRoleToUser(RoleToUserDto roleToUserDto);

    public void createUser(UserDto userDto);

}
