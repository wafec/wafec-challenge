package ecore.roles.app.transformers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import ecore.roles.app.dao.entities.RoleEntity;
import ecore.roles.api.transfer.Role;
import ecore.roles.app.dao.jpa.entities.RoleEntityJPA;
import ecore.roles.app.dao.jpa.projections.RoleProjectionJPA;

@Mapper
public interface RoleMapper {
	RoleMapper INSTANCE = Mappers.getMapper( RoleMapper.class );

	@Mapping( source = "id", target = "roleId" )
	Role roleEntityToRole( RoleEntity roleEntity );

	RoleEntity roleEntityJPAToRoleEntity( RoleEntityJPA roleEntityJPA );

	@Mapping( source = "roleId", target = "id" )
	@Mapping( source = "roleName", target = "name" )
	RoleEntity roleProjectionJPAToRoleEntity( RoleProjectionJPA roleProjectionJPA );
}
