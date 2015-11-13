package org.haw.praktikum2.old.server.core.connection.state;

import java.io.IOException;
import java.util.StringTokenizer;
import org.haw.praktikum2.old.server.core.connection.ChatServerConnection;

public class NotConnectedState implements ChatServerConnectionState {
	@Override
	public String handle(ChatServerConnection connection, String command, StringTokenizer requestTokenizer) throws IOException {
		switch(command) {
			case "hello": return hello(connection, requestTokenizer);
			case "quit":  return quit(connection, requestTokenizer);
			default:      return "BAD_PROTOCOL";
		}
	}
	
	private String hello(ChatServerConnection connection, StringTokenizer requestTokenizer) throws IOException {
		String username = requestTokenizer.nextToken();
		boolean accept = acceptUsername(username);
		if(accept) {
			connection.setUsername(username);
			connection.setConnectionState(state); // TODO: connected-State
		}
		return accept ? "ok" : "decline";
	}
	
	private boolean acceptUsername(String username) {
		return username != null && !username.trim().isEmpty();
	}
	
	private String quit(ChatServerConnection connection, StringTokenizer requestTokenizer) throws IOException {
		connection.setServiceRequested(false);
		return "bye";
	}
}
