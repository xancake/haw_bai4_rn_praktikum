package org.haw.praktikum2.old.shared.protocol.quit;

import org.haw.praktikum2.old.shared.ChatServer;
import org.haw.praktikum2.old.shared.connection.ChatConnection;
import org.haw.praktikum2.old.shared.exception.ChatException;
import org.haw.praktikum2.old.shared.protocol.AbstractRequest;
import org.haw.praktikum2.old.shared.protocol.Response;

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
