package ecore.roles.app.dao.jpa;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ecore.roles.app.dao.RoleDao;
import ecore.roles.app.dao.entities.RoleEntity;
import ecore.roles.app.dao.jpa.entities.RoleEntityJPA;
import ecore.roles.app.dao.jpa.repositories.RoleRepository;
import ecore.roles.app.transformers.RoleMapper;

@Component
public class RoleDaoJPA implements RoleDao {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleDaoJPA( RoleRepository roleRepository ) {
		this.roleRepository = roleRepository;
	}

	@Override
	public RoleEntity create( final String name ) {
		RoleEntityJPA roleEntityJPA = RoleEntityJPA.builder()
				.name( name )
				.roleId( UUID.randomUUID().toString() )
				.build();
		return RoleMapper.INSTANCE.roleEntityJPAToRoleEntity( roleRepository.save( roleEntityJPA ) );
	}
}
