package ecore.roles.app.dao.jpa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecore.roles.app.dao.entities.RoleMembershipEntity;
import ecore.roles.app.dao.jpa.entities.RoleMembershipEntityJPA;
import ecore.roles.app.dao.jpa.repositories.RoleMembershipRepository;

@ExtendWith( MockitoExtension.class )
public class RoleMembershipDaoJPATest {
	@Mock
	private RoleMembershipRepository roleMembershipRepository;

	@Captor
	private ArgumentCaptor<RoleMembershipEntityJPA> roleMembershipCaptor;

	private RoleMembershipDaoJPA testObj;

	private static final String ROLE_ID = UUID.randomUUID().toString();
	private static final String TEAM_ID = UUID.randomUUID().toString();
	private static final String USER_ID = UUID.randomUUID().toString();

	@BeforeEach
	public void setUp() {
		testObj = new RoleMembershipDaoJPA( roleMembershipRepository );
	}

	@Test
	void create_HappyPath() {
		when( roleMembershipRepository.save( any() ) ).thenReturn( getRoleMembershipEntityJPA() );

		RoleMembershipEntity roleMembershipEntity = testObj.create( ROLE_ID, USER_ID, TEAM_ID );

		assertThat( roleMembershipEntity.getRoleId(), equalTo( ROLE_ID ) );
		assertThat( roleMembershipEntity.getTeamId(), equalTo( TEAM_ID ) );
		assertThat( roleMembershipEntity.getUserId(), equalTo( USER_ID ) );
		verify( roleMembershipRepository ).save( roleMembershipCaptor.capture() );
		RoleMembershipEntityJPA roleMembershipEntityJPA = roleMembershipCaptor.getValue();
		assertThat( roleMembershipEntityJPA.getRoleId(), equalTo( ROLE_ID ) );
		assertThat( roleMembershipEntityJPA.getTeamId(), equalTo( TEAM_ID ) );
		assertThat( roleMembershipEntityJPA.getUserId(), equalTo( USER_ID ) );
	}

	private static RoleMembershipEntityJPA getRoleMembershipEntityJPA() {
		return RoleMembershipEntityJPA.builder()
				.roleId( ROLE_ID )
				.userId( USER_ID )
				.teamId( TEAM_ID )
				.build();
	}
}
