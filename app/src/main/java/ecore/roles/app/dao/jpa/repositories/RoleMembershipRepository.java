package ecore.roles.app.dao.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ecore.roles.app.dao.jpa.entities.RoleMembershipEntityJPA;
import ecore.roles.app.dao.jpa.projections.RoleProjectionJPA;

@Repository
public interface RoleMembershipRepository extends CrudRepository<RoleMembershipEntityJPA, Long> {
	@Query( "SELECT DISTINCT new ecore.roles.app.dao.jpa.projections.RoleProjectionJPA( r.roleId, r.name ) FROM RoleEntityJPA r, RoleMembershipEntityJPA rm WHERE rm.userId = :userId AND rm.teamId = :teamId AND r.roleId = rm.roleId" )
	List<RoleProjectionJPA> getRolesByMembership( String userId, String teamId );

	@Query( "FROM RoleMembershipEntityJPA rm WHERE rm.roleId = :roleId" )
	List<RoleMembershipEntityJPA> getRoleMembershipsByRole( String roleId );
}
