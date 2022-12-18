package ecore.roles.app.dao.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
@Table( name = "roles" )
public class RoleEntityJPA {
	@Id
	@Column( name = "id" )
	private long id;
	@Column( name = "role_id" )
	private String roleId;
	@Column( name = "name" )
	private String name;
}
