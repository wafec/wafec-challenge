package ecore.roles.app.transformers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import ecore.roles.api.transfer.Membership;
import ecore.roles.api.transfer.RoleMembership;
import ecore.roles.app.dao.entities.RoleMembershipEntity;

public class RoleMembershipMapperTest {
	private static final String ROLE_ID = UUID.randomUUID().toString();
	private static final String USER_ID = UUID.randomUUID().toString();
	private static final String TEAM_ID = UUID.randomUUID().toString();

	@Test
	void shouldMapRoleMembershipEntityToRoleMembership() {
		RoleMembershipEntity roleMembershipEntity = getRoleMembershipEntity();

		RoleMembership roleMembership = RoleMembershipMapper.INSTANCE.roleMembershipEntityToRoleMembership( roleMembershipEntity );

		assertThat( roleMembership.getRoleId(), equalTo( ROLE_ID ) );
		assertThat( roleMembership.getUserId(), equalTo( USER_ID ) );
		assertThat( roleMembership.getTeamId(), equalTo( TEAM_ID ) );
	}

	@Test
	void shouldMapRoleMembershipEntityToMembership() {
		RoleMembershipEntity roleMembershipEntity = getRoleMembershipEntity();

		Membership membership = RoleMembershipMapper.INSTANCE.roleMembershipEntityToMembership( roleMembershipEntity );

		assertThat( membership.getTeamId(), equalTo( TEAM_ID ) );
		assertThat( membership.getUserId(), equalTo( USER_ID ) );
	}

	private static RoleMembershipEntity getRoleMembershipEntity() {
		RoleMembershipEntity roleMembershipEntity = new RoleMembershipEntity();
		roleMembershipEntity.setRoleId( ROLE_ID );
		roleMembershipEntity.setUserId( USER_ID );
		roleMembershipEntity.setTeamId( TEAM_ID );
		return roleMembershipEntity;
	}
}
