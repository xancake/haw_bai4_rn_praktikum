package org.haw.praktikum2.shared.protocol.quit;

import org.haw.praktikum2.shared.ChatClient;
import org.haw.praktikum2.shared.connection.ChatConnection;
import org.haw.praktikum2.shared.exception.ChatException;
import org.haw.praktikum2.shared.protocol.AbstractResponse;
import org.haw.praktikum2.shared.protocol.Request;

@SuppressWarnings("serial")
public class QuitResponse extends AbstractResponse {
	public QuitResponse(Request request) {
		super(request);
	}
	
	@Override
	protected void execute0(ChatClient client, ChatConnection connection) throws ChatException {
		// TODO: Client beenden (?)
	}
}
