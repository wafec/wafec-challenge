package ecore.roles.api.transfer;

import lombok.Data;

@Data
public class RoleMembership {
	private String roleId;
	private String userId;
	private String teamId;
}
