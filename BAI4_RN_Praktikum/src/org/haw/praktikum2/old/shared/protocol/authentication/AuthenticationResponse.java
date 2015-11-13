package org.haw.praktikum2.old.shared.protocol.authentication;

import org.haw.praktikum2.old.shared.ChatClient;
import org.haw.praktikum2.old.shared.connection.ChatConnection;
import org.haw.praktikum2.old.shared.exception.ChatException;
import org.haw.praktikum2.old.shared.protocol.AbstractResponse;
import org.haw.praktikum2.old.shared.protocol.Request;
import org.haw.praktikum2.old.shared.protocol.Response;

@SuppressWarnings("serial")
public class AuthenticationResponse extends AbstractResponse implements Response {
	public AuthenticationResponse(Request request) {
		super(request);
	}
	
	public AuthenticationResponse(Request request, ChatException exception) {
		super(request, exception);
	}
	
	@Override
	public void execute0(ChatClient client, ChatConnection connection) throws ChatException {
		// TODO: 
	}
}
