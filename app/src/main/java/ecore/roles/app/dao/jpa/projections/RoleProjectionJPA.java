package ecore.roles.app.dao.jpa.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoleProjectionJPA {
	private String roleId;
	private String roleName;
}
