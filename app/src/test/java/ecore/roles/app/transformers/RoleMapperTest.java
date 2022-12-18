package ecore.roles.app.transformers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import ecore.roles.api.transfer.Role;
import ecore.roles.app.dao.entities.RoleEntity;

public class RoleMapperTest {
	private static String ROLE_ID = UUID.randomUUID().toString();
	private static String ROLE_NAME = "Role Example";

	@Test
	void shouldMapRoleEntityToRole() {
		RoleEntity roleEntity = getRoleEntity();

		Role role = RoleMapper.INSTANCE.roleEntityToRole( roleEntity );

		assertThat( role.getRoleId(), equalTo( ROLE_ID ) );
		assertThat( role.getName(), equalTo( ROLE_NAME ) );
	}

	private static RoleEntity getRoleEntity() {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setId( ROLE_ID );
		roleEntity.setName( ROLE_NAME );
		return roleEntity;
	}
}
