package ecore.roles.app.dao.entities;

import lombok.Data;

@Data
public class RoleMembershipEntity {
	private String roleId;
	private String userId;
	private String teamId;
}
