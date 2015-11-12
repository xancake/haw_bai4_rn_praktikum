package org.haw.praktikum2.shared;

import org.haw.praktikum2.shared.connection.ChatConnection;
import org.haw.praktikum2.shared.exception.ChatException;

public interface ChatServer {
	
	void authenticate(ChatConnection connection, String username) throws ChatException;
	
	
	void message(ChatConnection connection, String message) throws ChatException;
	
	
	void quit(ChatConnection connection) throws ChatException;
}
