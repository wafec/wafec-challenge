package ecore.roles.app.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.common.collect.ImmutableSet;

import ecore.roles.api.transfer.Membership;
import ecore.roles.api.transfer.Role;
import ecore.roles.api.transfer.RoleCreationRequest;
import ecore.roles.api.transfer.RoleMembership;
import ecore.roles.app.dao.RoleDao;
import ecore.roles.app.dao.RoleMembershipDao;
import ecore.roles.app.dao.entities.RoleEntity;
import ecore.roles.app.dao.entities.RoleMembershipEntity;
import ecore.roles.clients.TeamManagementClient;
import ecore.roles.clients.transfer.Team;

@ExtendWith( MockitoExtension.class )
public class RoleManagementApiImplTest {
	@Mock
	private RoleDao roleDao;

	@Mock
	private RoleMembershipDao roleMembershipDao;

	@Mock
	private TeamManagementClient teamManagementClient;

	private RoleManagementApiImpl testObj;

	private static final String ROLE_ID = UUID.randomUUID().toString();
	private static final String ROLE_NAME = "Example_Role_Name";
	private static final String TEAM_ID = UUID.randomUUID().toString();
	private static final String USER_ID = UUID.randomUUID().toString();
	private static final String USER_ID_ALTERNATIVE = UUID.randomUUID().toString();

	private static Object[] provideInvalidRoleNames() {
		return new Object[] { null, "" };
	}

	private static Object[] provideInvalidTeams() {
		return new Object[] { Arguments.of( null, IllegalArgumentException.class ),
							  Arguments.of( getTeam( TEAM_ID, USER_ID_ALTERNATIVE ), IllegalStateException.class ) };
	}

	@BeforeEach
	public void setUp() {
		testObj = new RoleManagementApiImpl( roleDao, roleMembershipDao, teamManagementClient );
	}

	@Test
	void createRole_PassResult() {
		RoleCreationRequest roleCreationRequest = getRoleCreationRequest( ROLE_NAME );
		when( roleDao.create( ROLE_NAME ) ).thenReturn( getRoleEntity() );

		Role role = testObj.createRole( roleCreationRequest );

		assertThat( role.getRoleId(), equalTo( ROLE_ID ) );
		assertThat( role.getName(), equalTo( ROLE_NAME ) );
	}

	@ParameterizedTest
	@MethodSource( "provideInvalidRoleNames" )
	void createRole_InvalidRoleNames( String roleName ) {
		final RoleCreationRequest roleCreationRequest = getRoleCreationRequest( roleName );

		Assertions.assertThrows( IllegalArgumentException.class, () -> testObj.createRole( roleCreationRequest ) );
	}

	@Test
	void attachRole_PassResult() {
		Membership membership = getMembership();
		when( roleMembershipDao.create( ROLE_ID, USER_ID, TEAM_ID ) ).thenReturn( getRoleMembershipEntity() );
		when( teamManagementClient.getTeam( TEAM_ID ) ).thenReturn( getTeam( TEAM_ID, USER_ID) );

		RoleMembership roleMembership = testObj.attachRole( ROLE_ID, membership );

		assertThat( roleMembership.getRoleId(), equalTo( ROLE_ID ) );
		assertThat( roleMembership.getUserId(), equalTo( USER_ID ) );
		assertThat( roleMembership.getTeamId(), equalTo( TEAM_ID ) );
	}

	@ParameterizedTest
	@MethodSource( "provideInvalidTeams" )
	void attachRole_InvalidTeams( Team team, Class<? extends Exception> exceptionType ) {
		final Membership membership = getMembership();
		when( teamManagementClient.getTeam( any() ) ).thenReturn( team );

		Assertions.assertThrows( exceptionType, () -> testObj.attachRole( ROLE_ID, membership ) );
	}

	@Test
	void attachRole_NullMembership() {
		Assertions.assertThrows( NullPointerException.class, () -> testObj.attachRole( ROLE_ID, null ) );
	}

	@Test
	void getRolesForMembership_PassResult() {
		when( roleMembershipDao.getRolesByMembership( USER_ID, TEAM_ID ) ).thenReturn( ImmutableSet.of( getRoleEntity() ) );

		Set<Role> roles = testObj.getRolesForMembership( USER_ID, TEAM_ID );

		assertThat( roles, hasSize( 1 ) );
		Role role = roles.iterator().next();
		assertThat( role.getRoleId(), equalTo( ROLE_ID ) );
		assertThat( role.getName(), equalTo( ROLE_NAME ) );
	}

	@Test
	void getRolesForMembership_Empty() {
		when( roleMembershipDao.getRolesByMembership( USER_ID, TEAM_ID ) ).thenReturn( Collections.emptySet() );

		Set<Role> roles = testObj.getRolesForMembership( USER_ID, TEAM_ID );

		assertThat( roles, hasSize( 0 ) );
	}

	@Test
	void getMembershipsForRole_PassResult() {
		when( roleMembershipDao.getRoleMembershipsByRole( ROLE_ID ) ).thenReturn( ImmutableSet.of( getRoleMembershipEntity() ) );

		Set<Membership> memberships = testObj.getMembershipsForRole( ROLE_ID );

		assertThat( memberships, hasSize( 1 ) );
		Membership membership = memberships.iterator().next();
		assertThat( membership.getUserId(), equalTo( USER_ID ) );
		assertThat( membership.getTeamId(), equalTo( TEAM_ID ) );
	}

	private static RoleCreationRequest getRoleCreationRequest( String roleName ) {
		RoleCreationRequest roleCreationRequest = new RoleCreationRequest();
		roleCreationRequest.setName( roleName );
		return roleCreationRequest;
	}

	private static RoleEntity getRoleEntity() {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setName( ROLE_NAME );
		roleEntity.setId( ROLE_ID );
		return roleEntity;
	}

	private static Membership getMembership() {
		Membership membership = new Membership();
		membership.setTeamId( TEAM_ID );
		membership.setUserId( USER_ID );
		return membership;
	}

	private static RoleMembershipEntity getRoleMembershipEntity() {
		RoleMembershipEntity roleMembershipEntity = new RoleMembershipEntity();
		roleMembershipEntity.setRoleId( ROLE_ID );
		roleMembershipEntity.setUserId( USER_ID );
		roleMembershipEntity.setTeamId( TEAM_ID );
		return roleMembershipEntity;
	}

	private static Team getTeam( String teamId, String userId ) {
		Team team = new Team();
		team.setId( teamId );
		team.setTeamMemberIds( ImmutableSet.of( userId ) );
		team.setTeamLeadId( userId );
		team.setName( "Example Team Name" );
		return team;
	}
}
