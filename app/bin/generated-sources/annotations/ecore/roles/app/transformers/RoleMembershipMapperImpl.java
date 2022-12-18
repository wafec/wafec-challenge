package ecore.roles.app.transformers;

import ecore.roles.api.transfer.Membership;
import ecore.roles.api.transfer.RoleMembership;
import ecore.roles.app.dao.entities.RoleMembershipEntity;
import ecore.roles.app.dao.jpa.entities.RoleMembershipEntityJPA;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-18T16:42:33-0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.5 (Eclipse Adoptium)"
)
public class RoleMembershipMapperImpl implements RoleMembershipMapper {

    @Override
    public RoleMembership roleMembershipEntityToRoleMembership(RoleMembershipEntity roleMembershipEntity) {
        if ( roleMembershipEntity == null ) {
            return null;
        }

        RoleMembership roleMembership = new RoleMembership();

        roleMembership.setRoleId( roleMembershipEntity.getRoleId() );
        roleMembership.setTeamId( roleMembershipEntity.getTeamId() );
        roleMembership.setUserId( roleMembershipEntity.getUserId() );

        return roleMembership;
    }

    @Override
    public Membership roleMembershipEntityToMembership(RoleMembershipEntity roleMembershipEntity) {
        if ( roleMembershipEntity == null ) {
            return null;
        }

        Membership membership = new Membership();

        membership.setTeamId( roleMembershipEntity.getTeamId() );
        membership.setUserId( roleMembershipEntity.getUserId() );

        return membership;
    }

    @Override
    public RoleMembershipEntity roleMembershipEntityJPAToRoleMembershipEntity(RoleMembershipEntityJPA roleMembershipEntityJPA) {
        if ( roleMembershipEntityJPA == null ) {
            return null;
        }

        RoleMembershipEntity roleMembershipEntity = new RoleMembershipEntity();

        roleMembershipEntity.setRoleId( roleMembershipEntityJPA.getRoleId() );
        roleMembershipEntity.setTeamId( roleMembershipEntityJPA.getTeamId() );
        roleMembershipEntity.setUserId( roleMembershipEntityJPA.getUserId() );

        return roleMembershipEntity;
    }
}
