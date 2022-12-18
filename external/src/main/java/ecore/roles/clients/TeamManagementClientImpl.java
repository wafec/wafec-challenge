package ecore.roles.clients;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ecore.roles.clients.transfer.Team;

@Component
public class TeamManagementClientImpl implements TeamManagementClient {
	private final RestTemplate restTemplate;

	@Value( "${Services.Team.Url:" + HOST_DEFAULT + "}" )
	private String host;

	private static final String HOST_DEFAULT = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com/teams/";

	@Autowired
	public TeamManagementClientImpl( final RestTemplate restTemplate ) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Team getTeam( final String id ) {
		return restTemplate.getForObject( host + Objects.requireNonNull( id ), Team.class );
	}
}
