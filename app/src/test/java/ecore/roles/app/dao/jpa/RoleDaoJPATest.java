package ecore.roles.app.dao.jpa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecore.roles.app.dao.entities.RoleEntity;
import ecore.roles.app.dao.jpa.entities.RoleEntityJPA;
import ecore.roles.app.dao.jpa.repositories.RoleRepository;

@ExtendWith( MockitoExtension.class )
public class RoleDaoJPATest {
	@Mock
	private RoleRepository roleRepository;

	@Captor
	private ArgumentCaptor<RoleEntityJPA> roleCaptor;

	private RoleDaoJPA testObj;

	private static final String ROLE_NAME = "Example Role";

	@BeforeEach
	public void setUp() {
		testObj = new RoleDaoJPA( roleRepository );
	}

	@Test
	void create_HappyPath() {
		when( roleRepository.save( any() ) ).thenReturn( getRoleEntityJPA() );

		RoleEntity roleEntity = testObj.create( ROLE_NAME );

		assertThat( roleEntity.getName(), equalTo( ROLE_NAME ) );
		verify( roleRepository ).save( roleCaptor.capture() );
		RoleEntityJPA roleEntityJPA = roleCaptor.getValue();
		assertThat( roleEntityJPA.getRoleId(), not( nullValue() ) );
		assertThat( roleEntityJPA.getName(), equalTo( ROLE_NAME ) );
	}

	private static RoleEntityJPA getRoleEntityJPA() {
		return RoleEntityJPA.builder()
				.name( ROLE_NAME )
				.build();
	}
}
