package org.haw.praktikum2.old.shared.protocol.quit;

import org.haw.praktikum2.old.shared.ChatClient;
import org.haw.praktikum2.old.shared.connection.ChatConnection;
import org.haw.praktikum2.old.shared.exception.ChatException;
import org.haw.praktikum2.old.shared.protocol.AbstractResponse;
import org.haw.praktikum2.old.shared.protocol.Request;

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
