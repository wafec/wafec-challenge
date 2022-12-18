package ecore.roles.app.transformers;

import ecore.roles.api.transfer.Role;
import ecore.roles.app.dao.entities.RoleEntity;
import ecore.roles.app.dao.jpa.entities.RoleEntityJPA;
import ecore.roles.app.dao.jpa.projections.RoleProjectionJPA;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-18T16:42:33-0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.5 (Eclipse Adoptium)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role roleEntityToRole(RoleEntity roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        Role role = new Role();

        role.setRoleId( roleEntity.getId() );
        role.setName( roleEntity.getName() );

        return role;
    }

    @Override
    public RoleEntity roleEntityJPAToRoleEntity(RoleEntityJPA roleEntityJPA) {
        if ( roleEntityJPA == null ) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId( String.valueOf( roleEntityJPA.getId() ) );
        roleEntity.setName( roleEntityJPA.getName() );

        return roleEntity;
    }

    @Override
    public RoleEntity roleProjectionJPAToRoleEntity(RoleProjectionJPA roleProjectionJPA) {
        if ( roleProjectionJPA == null ) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId( roleProjectionJPA.getRoleId() );
        roleEntity.setName( roleProjectionJPA.getRoleName() );

        return roleEntity;
    }
}
