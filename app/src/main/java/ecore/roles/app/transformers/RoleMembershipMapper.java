package ecore.roles.app.transformers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ecore.roles.api.transfer.Membership;
import ecore.roles.app.dao.entities.RoleMembershipEntity;
import ecore.roles.api.transfer.RoleMembership;
import ecore.roles.app.dao.jpa.entities.RoleMembershipEntityJPA;

@Mapper
public interface RoleMembershipMapper {
	RoleMembershipMapper INSTANCE = Mappers.getMapper( RoleMembershipMapper.class );

	RoleMembership roleMembershipEntityToRoleMembership( RoleMembershipEntity roleMembershipEntity );

	Membership roleMembershipEntityToMembership( RoleMembershipEntity roleMembershipEntity );

	RoleMembershipEntity roleMembershipEntityJPAToRoleMembershipEntity( RoleMembershipEntityJPA roleMembershipEntityJPA );
}
