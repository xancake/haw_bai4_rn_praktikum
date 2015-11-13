package org.haw.praktikum2.old.shared.protocol;

import java.io.Serializable;
import org.haw.praktikum2.old.shared.ChatServer;
import org.haw.praktikum2.old.shared.connection.ChatConnection;

public interface Request extends Serializable {
	/**
	 * Beantwortet dieses Request und erzeugt einen entsprechenden {@link Response}.
	 * @param server Der Server der dieses Request beantwortet
	 * @param connection Die Connection, die das Request empfangen hat
	 * @return Der {@link Response} zu diesem Request
	 */
	Response respond(ChatServer server, ChatConnection connection);
}
