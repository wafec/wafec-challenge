package ecore.roles.clients.transfer;

import java.util.Set;

import lombok.Data;

@Data
public class Team {
	private String id;
	private String name;
	private String teamLeadId;
	private Set<String> teamMemberIds;
}
