package ecore.roles.app.dao;

import ecore.roles.app.dao.entities.RoleEntity;

public interface RoleDao {
	RoleEntity create( String name );
}
