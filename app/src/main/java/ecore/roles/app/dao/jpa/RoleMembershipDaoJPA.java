package ecore.roles.app.dao.jpa;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ecore.roles.app.dao.RoleMembershipDao;
import ecore.roles.app.dao.entities.RoleEntity;
import ecore.roles.app.dao.entities.RoleMembershipEntity;
import ecore.roles.app.dao.jpa.entities.RoleMembershipEntityJPA;
import ecore.roles.app.dao.jpa.repositories.RoleMembershipRepository;
import ecore.roles.app.transformers.RoleMapper;
import ecore.roles.app.transformers.RoleMembershipMapper;

@Component
public class RoleMembershipDaoJPA implements RoleMembershipDao {
	private final RoleMembershipRepository roleMembershipRepository;

	@Autowired
	public RoleMembershipDaoJPA( final RoleMembershipRepository roleMembershipRepository ) {
		this.roleMembershipRepository = roleMembershipRepository;
	}

	@Override
	public RoleMembershipEntity create( final String roleId, final String userId,
			final String teamId ) {
		RoleMembershipEntityJPA roleMembershipEntityJPA = RoleMembershipEntityJPA.builder()
				.roleId( roleId )
				.userId( userId )
				.teamId( teamId )
				.build();
		return RoleMembershipMapper.INSTANCE
				.roleMembershipEntityJPAToRoleMembershipEntity(
						roleMembershipRepository.save( roleMembershipEntityJPA ) );
	}

	@Override
	public Set<RoleEntity> getRolesByMembership( final String userId, final String teamId ) {
		return roleMembershipRepository.getRolesByMembership( userId, teamId )
				.stream().map( RoleMapper.INSTANCE::roleProjectionJPAToRoleEntity ).collect(
						Collectors.toSet() );
	}

	@Override
	public Set<RoleMembershipEntity> getRoleMembershipsByRole( final String roleId ) {
		return roleMembershipRepository.getRoleMembershipsByRole( roleId )
				.stream().map( RoleMembershipMapper.INSTANCE::roleMembershipEntityJPAToRoleMembershipEntity )
				.collect( Collectors.toSet() );
	}
}
