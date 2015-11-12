package org.haw.praktikum2.shared.protocol.quit;

import org.haw.praktikum2.shared.ChatServer;
import org.haw.praktikum2.shared.connection.ChatConnection;
import org.haw.praktikum2.shared.exception.ChatException;
import org.haw.praktikum2.shared.protocol.AbstractRequest;
import org.haw.praktikum2.shared.protocol.Response;

@SuppressWarnings("serial")
public class QuitRequest extends AbstractRequest {
	@Override
	public Response respond(ChatServer server, ChatConnection connection) {
		try {
			server.quit(connection);
			return new QuitResponse(this);
		} catch (ChatException e) {
			return new QuitResponse(this);
		}
	}
}
