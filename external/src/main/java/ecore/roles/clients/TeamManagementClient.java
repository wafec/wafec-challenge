package ecore.roles.clients;

import ecore.roles.clients.transfer.Team;

public interface TeamManagementClient {

	Team getTeam( String id );
}
