package org.haw.praktikum2.old.shared;

import org.haw.praktikum2.old.shared.connection.ChatConnection;
import org.haw.praktikum2.old.shared.exception.ChatException;

public interface ChatServer {
	
	void authenticate(ChatConnection connection, String username) throws ChatException;
	
	
	void message(ChatConnection connection, String message) throws ChatException;
	
	
	void quit(ChatConnection connection) throws ChatException;
}
