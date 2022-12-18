package ecore.roles.app.dao.jpa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ecore.roles.app.dao.jpa.entities.RoleEntityJPA;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntityJPA, Long> {

}
