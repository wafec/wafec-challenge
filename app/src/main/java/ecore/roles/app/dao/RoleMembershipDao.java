package ecore.roles.app.dao;

import java.util.Set;

import ecore.roles.app.dao.entities.RoleEntity;
import ecore.roles.app.dao.entities.RoleMembershipEntity;

public interface RoleMembershipDao {
	RoleMembershipEntity create( String roleId, String userId, String teamId );

	Set<RoleEntity> getRolesByMembership( String userId, String teamId );

	Set<RoleMembershipEntity> getRoleMembershipsByRole( String roleId );
}
