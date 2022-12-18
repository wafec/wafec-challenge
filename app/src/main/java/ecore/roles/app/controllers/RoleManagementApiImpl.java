package ecore.roles.app.controllers;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecore.roles.api.RoleManagementApi;
import ecore.roles.clients.TeamManagementClient;
import ecore.roles.clients.transfer.Team;
import ecore.roles.app.dao.RoleDao;
import ecore.roles.app.dao.RoleMembershipDao;
import ecore.roles.app.dao.entities.RoleEntity;
import ecore.roles.app.dao.entities.RoleMembershipEntity;
import ecore.roles.api.transfer.Membership;
import ecore.roles.api.transfer.Role;
import ecore.roles.api.transfer.RoleCreationRequest;
import ecore.roles.api.transfer.RoleMembership;
import ecore.roles.app.transformers.RoleMapper;
import ecore.roles.app.transformers.RoleMembershipMapper;

@RestController
@RequestMapping("/api/roles")
public class RoleManagementApiImpl implements RoleManagementApi {
	private final RoleDao roleDao;
	private final RoleMembershipDao roleMembershipDao;
	private final TeamManagementClient teamManagementClient;

	@Autowired
	public RoleManagementApiImpl( final RoleDao roleDao,
			final RoleMembershipDao roleMembershipDao,
			final TeamManagementClient teamManagementClient ) {
		this.roleDao = roleDao;
		this.roleMembershipDao = roleMembershipDao;
		this.teamManagementClient = teamManagementClient;
	}

	@PostMapping
	@Override
	public Role createRole( @RequestBody final RoleCreationRequest roleCreationRequest ) {
		if ( Strings.isEmpty( roleCreationRequest.getName() ) ) {
			throw new IllegalArgumentException( "Name cannot be empty or null" );
		}

		RoleEntity roleEntity = roleDao.create( roleCreationRequest.getName() );
		return RoleMapper.INSTANCE.roleEntityToRole( roleEntity );
	}

	@PostMapping("/{id}/membership")
	@Override
	public RoleMembership attachRole( @PathVariable("id") final String roleId, @RequestBody final Membership membership ) {
		Team team = teamManagementClient.getTeam( Objects.requireNonNull( membership ).getTeamId() );
		if ( team == null ) {
			throw new IllegalArgumentException( "Team not found" );
		}
		if ( !team.getTeamMemberIds().contains( membership.getUserId() ) ) {
			throw new IllegalStateException( "User is not member of this team" );
		}

		RoleMembershipEntity roleMembershipEntity = roleMembershipDao.create( roleId, membership.getUserId(), membership.getTeamId() );
		return RoleMembershipMapper.INSTANCE.roleMembershipEntityToRoleMembership( roleMembershipEntity );
	}

	@GetMapping
	@Override
	public Set<Role> getRolesForMembership( @RequestParam final String userId, @RequestParam final String teamId ) {
		final Set<RoleEntity> roleEntities = roleMembershipDao.getRolesByMembership( userId, teamId );
		return roleEntities.stream().map( RoleMapper.INSTANCE::roleEntityToRole ).collect( Collectors.toSet() );
	}

	@GetMapping("/membership")
	@Override
	public Set<Membership> getMembershipsForRole( @RequestParam final String roleId ) {
		final Set<RoleMembershipEntity> roleMembershipEntities = roleMembershipDao.getRoleMembershipsByRole( roleId );
		return roleMembershipEntities.stream().map( RoleMembershipMapper.INSTANCE::roleMembershipEntityToMembership ).collect( Collectors.toSet() );
	}
}
