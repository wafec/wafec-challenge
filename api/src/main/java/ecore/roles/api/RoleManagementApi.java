package ecore.roles.api;

import java.util.Set;

import ecore.roles.api.transfer.Membership;
import ecore.roles.api.transfer.Role;
import ecore.roles.api.transfer.RoleCreationRequest;
import ecore.roles.api.transfer.RoleMembership;

public interface RoleManagementApi {

	Role createRole( RoleCreationRequest roleCreationRequest );

	RoleMembership attachRole( String roleId, Membership membership );

	Set<Role> getRolesForMembership( String userId, String teamId );

	Set<Membership> getMembershipsForRole( String roleId );
}
