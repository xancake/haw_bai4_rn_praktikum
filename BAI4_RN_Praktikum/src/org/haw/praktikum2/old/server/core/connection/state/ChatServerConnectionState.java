package org.haw.praktikum2.old.server.core.connection.state;

import java.io.IOException;
import java.util.StringTokenizer;
import org.haw.praktikum2.old.server.core.connection.ChatServerConnection;

public interface ChatServerConnectionState {
	
	String handle(ChatServerConnection connection, String command, StringTokenizer parameters) throws IOException;
}
